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
import vuluu.userservice.dto.request.ProjectRequestDTO;
import vuluu.userservice.dto.request.WorkExperienceRequestDTO;
import vuluu.userservice.dto.response.MessageResponseDTO;
import vuluu.userservice.entity.Applicant;
import vuluu.userservice.entity.Education;
import vuluu.userservice.entity.Project;
import vuluu.userservice.entity.WorkExperience;
import vuluu.userservice.exception.AppException;
import vuluu.userservice.exception.ErrorCode;
import vuluu.userservice.mapper.ToApplicantMapper;
import vuluu.userservice.repository.ApplicantRepository;
import vuluu.userservice.repository.EmployerRepository;
import vuluu.userservice.repository.UserRepository;
import vuluu.userservice.service.kafka_producer.UploadImageProducer;
import vuluu.userservice.util.MyUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ApplicantService {

  EmployerRepository employerRepository;
  AddressService addressService;
  UserRepository userRepository;
  ApplicantRepository applicantRepository;
  ToApplicantMapper toApplicantMapper;
  MyUtils myUtils;
  UploadImageProducer uploadImageProducer;

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
    var applicant = toApplicantMapper.toApplicant(requestDTO, addressService);
    applicant.setUser(user);

    // setting education for applicant
    applicant.setEducations(toSetEducation(applicant, requestDTO.getEducationRequestDTO()));

    // setting work experience for applicant
    applicant.setWorkExperiences(toWorkExperience(applicant,
        requestDTO.getWorkExperienceRequestDTO()));

    // setting projects for applicant
    applicant.setProjects(toProject(applicant, requestDTO.getProjectRequestDTO()));

    // for applicant don't need to update role
    // update user website
    user.setWebsite(requestDTO.getWebsite());

    userRepository.save(user);
    // save new applicant
    applicantRepository.save(applicant);

    // using kafka to upload image
     uploadImageProducer.uploadUserProfile(user, requestDTO.getImg());

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

  private Set<Project> toProject(Applicant applicant, List<ProjectRequestDTO> requestDTO) {
    return requestDTO.stream()
        .map(projectRequestDTO -> Project
            .builder()
            .projectName(projectRequestDTO.getProjectName())
            .position(projectRequestDTO.getPosition())
            .startDate(projectRequestDTO.getStartDate())
            .endDate(projectRequestDTO.getEndDate())
            .description(projectRequestDTO.getDescription())
            .applicant(applicant)
            .build())
        .collect(Collectors.toSet());
  }
}
