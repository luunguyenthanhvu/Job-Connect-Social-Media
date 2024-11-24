package vuluu.userservice.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vuluu.userservice.dto.request.CreateAccountApplicantRequestDTO;
import vuluu.userservice.dto.request.EducationRequestDTO;
import vuluu.userservice.dto.request.WorkExperienceRequestDTO;
import vuluu.userservice.dto.response.MessageResponseDTO;
import vuluu.userservice.entity.Applicant;
import vuluu.userservice.entity.Education;
import vuluu.userservice.entity.WorkExperience;
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

    // setting education for applicant
    applicant.setEducations(toSetEducation(applicant, requestDTO.getEducationRequestDTO()));

    // setting work experience for applicant
    applicant.setWorkExperiences(toWorkExperience(applicant,
        requestDTO.getWorkExperienceRequestDTO()));

    // for applicant don't need to update role
    // save new applicant
    applicantRepository.save(applicant);

    return MessageResponseDTO.builder().message("Applicant create successfully").build();
  }

  private Set<Education> toSetEducation(Applicant applicant, List<EducationRequestDTO> requestDTO) {
    return requestDTO.stream()
        .map(educationRequestDTO -> Education
            .builder()
            .institutionName(educationRequestDTO.getInstitutionName())
            .fieldOfStudy(educationRequestDTO.getFieldOfStudy())
            .degree(educationRequestDTO.getDegree())
            .startDate(educationRequestDTO.getStartDate())
            .endDate(educationRequestDTO.getEndDate())
            .applicant(applicant)
            .build()).collect(Collectors.toSet());
  }

  private Set<WorkExperience> toWorkExperience(Applicant applicant,
      List<WorkExperienceRequestDTO> requestDTO) {
    return requestDTO.stream()
        .map(workExperienceRequestDTO -> WorkExperience
            .builder()
            .companyName(workExperienceRequestDTO.getCompanyName())
            .position(workExperienceRequestDTO.getPosition())
            .startDate(workExperienceRequestDTO.getStartDate())
            .endDate(workExperienceRequestDTO.getEndDate())
            .description(workExperienceRequestDTO.getDescription())
            .applicant(applicant)
            .build()).collect(Collectors.toSet());
  }
}
