package vuluu.userservice.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vuluu.userservice.dto.request.AccountVerifyRequestDTO;
import vuluu.userservice.dto.request.CreateAccountRequestDTO;
import vuluu.userservice.dto.response.MessageResponseDTO;
import vuluu.userservice.dto.response.UserResponseDTO;
import vuluu.userservice.entity.Role;
import vuluu.userservice.entity.User;
import vuluu.userservice.enums.ERole;
import vuluu.userservice.exception.AppException;
import vuluu.userservice.exception.ErrorCode;
import vuluu.userservice.mapper.UserMapper;
import vuluu.userservice.repository.RoleRepository;
import vuluu.userservice.repository.UserRepository;
import vuluu.userservice.service.kafka_producer.EmailProducer;
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
  EmailProducer emailProducer;
  String VERIFY_SUCCESS = "Your Account is verify.";
  String VERIFIED = "Your account has been verified.";

  @Transactional
  public UserResponseDTO createUser(CreateAccountRequestDTO requestDTO) {
    if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
      throw new AppException(ErrorCode.USER_EXISTED);
    }

    User user = userMapper.toUser(requestDTO);
    user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

    HashSet<Role> roles = new HashSet<>();
    roleRepository.findByRoleId(ERole.APPLICANT).ifPresent(roles::add);

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
      return MessageResponseDTO.builder().message(VERIFIED).build();
    }

    // check code
    if (!user.getVerifyCode().equals(requestDTO.getCode())) {
      throw new AppException(ErrorCode.VERIFY_TIME_OUT);
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

      // using kafka to send email to notification service
      emailProducer.sendEmail(user);
    }

    throw new AppException(ErrorCode.VERIFY_TIME_OUT);
  }

}
