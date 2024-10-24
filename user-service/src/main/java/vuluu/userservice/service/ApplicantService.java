package vuluu.userservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vuluu.userservice.dto.request.CreateAccountApplicantRequestDTO;
import vuluu.userservice.dto.response.MessageResponseDTO;
import vuluu.userservice.exception.AppException;
import vuluu.userservice.exception.ErrorCode;
import vuluu.userservice.mapper.ApplicantMapper;
import vuluu.userservice.repository.ApplicantRepository;
import vuluu.userservice.repository.EmployerRepository;
import vuluu.userservice.repository.UserRepository;
import vuluu.userservice.util.MyUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicantService {

  EmployerRepository employerRepository;
  AddressService addressService;
  UserRepository userRepository;
  ApplicantRepository applicantRepository;
  ApplicantMapper applicantMapper;
  MyUtils myUtils;

  @Transactional
  public MessageResponseDTO createApplicantAccount(CreateAccountApplicantRequestDTO requestDTO) {
    String userId = myUtils.getUserId();

    // If user not present throw error user not exist
    var user = userRepository.findById(userId)
        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

    // If user type exists throw error user existed
    if (employerRepository.existsById(userId) || applicantRepository.existsById(userId)) {
      throw new AppException(ErrorCode.USER_EXISTED);
    }

    // map from dto to entity
    var applicant = applicantMapper.toApplicant(requestDTO, addressService);
    applicant.setUser(user);

    // for applicant don't need to update role
    // save new applicant
    applicantRepository.save(applicant);

    return MessageResponseDTO.builder().message("Applicant create successfully").build();
  }
}
