package vuluu.userservice.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vuluu.userservice.dto.request.AccountVerifyRequestDTO;
import vuluu.userservice.dto.request.CreateAccountRequestDTO;
import vuluu.userservice.dto.request.EmployerInfoWithAddressRequestDTO;
import vuluu.userservice.dto.response.JobPostEmployerInfoAddressResponseDTO;
import vuluu.userservice.dto.response.MessageResponseDTO;
import vuluu.userservice.dto.response.UserResponseDTO;
import vuluu.userservice.entity.Role;
import vuluu.userservice.entity.User;
import vuluu.userservice.enums.ERole;
import vuluu.userservice.exception.AppException;
import vuluu.userservice.exception.ErrorCode;
import vuluu.userservice.mapper.UserMapper;
import vuluu.userservice.repository.AddressRepository;
import vuluu.userservice.repository.ApplicantRepository;
import vuluu.userservice.repository.EmployerRepository;
import vuluu.userservice.repository.RoleRepository;
import vuluu.userservice.repository.UserRepository;
import vuluu.userservice.service.kafka_producer.EmailProducer;
import vuluu.userservice.service.kafka_producer.ResetPasswordProducer;
import vuluu.userservice.util.MyUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {

  MyUtils myUtils;
  UserRepository userRepository;
  RoleRepository roleRepository;
  PasswordEncoder passwordEncoder;
  UserMapper userMapper;
  AddressRepository addressRepository;
  EmailProducer emailProducer;
  ResetPasswordProducer resetPasswordProducer;
  EmployerRepository employerRepository;
  ApplicantRepository applicantRepository;
  String VERIFY_SUCCESS = "Your Account is verify.";
  String VERIFY_TIME_OUT = "Verify code out date.";

  @Transactional
  public UserResponseDTO createUser(CreateAccountRequestDTO requestDTO) {
    if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
      throw new AppException(ErrorCode.USER_EXISTED);
    }

    User user = userMapper.toUser(requestDTO);
    user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

    HashSet<Role> roles = new HashSet<>();
    roleRepository.findByRoleId(ERole.USER).ifPresent(roles::add);

    user.setRoles(roles);
    user = userRepository.save(user);

    // generate verify code
    String code = myUtils.generateVerificationCode();
    user.setVerifyCode(code);

    // using kafka to send email to notification service
    emailProducer.sendEmail(user);

    return userMapper.toUserResponseDTO(user);
  }

  @Transactional
  public MessageResponseDTO verifyAccount(AccountVerifyRequestDTO requestDTO) {
    var user = userRepository.findByEmail(requestDTO.getEmail())
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    // check if verified
    if (user.isVerified()) {
      throw new AppException(ErrorCode.USER_VERIFIED);
    }

    // check code
    if (!user.getVerifyCode().equals(requestDTO.getCode())) {
      throw new AppException(ErrorCode.WRONG_VERIFY_CODE);
    }

    LocalDateTime current = LocalDateTime.now();

    Duration duration = Duration.between(user.getVerificationSentDate(), current);

    if (duration.toMinutes() <= 30) {
      user.setVerified(true);
      userRepository.save(user);
      return MessageResponseDTO.builder().message(VERIFY_SUCCESS).build();
    } else {
      // resend code
      // update verification sent date
      user.setVerificationSentDate(LocalDateTime.now());

      //update code
      String code = myUtils.generateVerificationCode();
      user.setVerifyCode(code);

      userRepository.save(user);
      System.out.println("Đã update code verify");
      // using kafka to send email to notification service
      emailProducer.sendEmail(user);
      return MessageResponseDTO.builder().message(VERIFY_TIME_OUT).build();
    }
  }

  // Phương thức tìm kiếm hình ảnh theo userId hoặc postId
  // Redis Cacheable check cho file dữ liệu
  @Cacheable(value = "userInfoCache", key = "'userInfo:' + #userId", unless = "#result == null")
  public UserResponseDTO getUser() {
    String userId = myUtils.getUserId();
    System.out.println("Kiểm tra user");
    // If user type exists throw error user existed
    if (employerRepository.existsById(userId) || applicantRepository.existsById(userId)) {
      return userMapper.toUserResponseDTO(userRepository.findById(userId).get());
    }
    throw new AppException(ErrorCode.USER_NOT_CHOSE_TYPE);
  }

  @Transactional
  public MessageResponseDTO resendCode(String email) {
    var user = userRepository.findByEmail(email)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    // check if verified
    if (user.isVerified()) {
      throw new AppException(ErrorCode.USER_VERIFIED);
    }

    // update verification sent date
    user.setVerificationSentDate(LocalDateTime.now());

    //update code
    String code = myUtils.generateVerificationCode();
    user.setVerifyCode(code);

    userRepository.save(user);

    // using kafka to send email to notification service
    emailProducer.sendEmail(user);

    return MessageResponseDTO.builder().message("Resend code successfully").build();
  }

  @Transactional
  public MessageResponseDTO resetPassword(String email) {
    var user = userRepository.findByEmail(email)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    String password = myUtils.generatePassword();
    user.setPassword(passwordEncoder.encode(password));

    // update user password
    userRepository.save(user);

    // using kafka to send new pass to user email
    resetPasswordProducer.sendResetPass(user, password);
    return MessageResponseDTO.builder().message("Reset password successfully").build();
  }

  // method get employer info with userId and addressId
  public List<JobPostEmployerInfoAddressResponseDTO> getEmployerWithAddress(
      List<EmployerInfoWithAddressRequestDTO> requestDTO) {
    var response = new ArrayList<JobPostEmployerInfoAddressResponseDTO>();
    requestDTO.forEach(dto -> {
      var employerInfoWithAddress = getListEmployerInfoWithAddress(dto.getUserId(),
          dto.getAddressId());
      employerInfoWithAddress.setPostId(dto.getPostId());
      response.add(employerInfoWithAddress);
    });
    return response;
  }

  @Cacheable(value = "employerInfoWithAddressCache",
      key = "'employerInfoWithAddress:' + #userId + #addressId", unless = "#result == null")
  public JobPostEmployerInfoAddressResponseDTO getListEmployerInfoWithAddress(
      String userId, Long addressId) {

    // Ensure userId and addressId are found
    var userOptional = userRepository.findById(userId);
    var addressOptional = addressRepository.findById(addressId);

    if (userOptional.isPresent() && addressOptional.isPresent()) {
      var username = userOptional.get().getUsername();
      var address = addressOptional.get().getAddressDescription();

      return JobPostEmployerInfoAddressResponseDTO.builder()
          .userId(userId)
          .address(address)
          .username(username) // Ensure username is set
          .build();
    } else {
      // Handle case where user or address is not found
      log.error("User info no address id or user not exits");
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
  }
}
