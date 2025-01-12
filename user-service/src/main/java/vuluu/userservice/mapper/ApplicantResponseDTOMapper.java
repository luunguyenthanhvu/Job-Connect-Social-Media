package vuluu.userservice.mapper;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import vuluu.userservice.dto.request.EducationRequestDTO;
import vuluu.userservice.dto.request.ProjectRequestDTO;
import vuluu.userservice.dto.request.WorkExperienceRequestDTO;
import vuluu.userservice.dto.response.ApplicantProfileResponseDTO;
import vuluu.userservice.entity.Address;
import vuluu.userservice.entity.Applicant;

@Component
public class ApplicantResponseDTOMapper {

  public ApplicantProfileResponseDTO toApplicantProfileResponseDTO(Applicant applicant) {
    // Mapping Education entities to DTOs
    List<EducationRequestDTO> educationDTOs = applicant.getEducations().stream()
        .map(education -> new EducationRequestDTO(
            education.getInstitutionName(),
            education.getFieldOfStudy(),
            education.getDegree(),
            education.getStartDate(),
            education.getEndDate()))
        .collect(Collectors.toList());

    // Mapping WorkExperience entities to DTOs
    List<WorkExperienceRequestDTO> workExperienceDTOs = applicant.getWorkExperiences().stream()
        .map(workExperience -> new WorkExperienceRequestDTO(
            workExperience.getCompanyName(),
            workExperience.getPosition(),
            workExperience.getStartDate(),
            workExperience.getEndDate(),
            workExperience.getDescription()))
        .collect(Collectors.toList());

    // Mapping Project entities to DTOs
    List<ProjectRequestDTO> projectDTOs = applicant.getProjects().stream()
        .map(project -> new ProjectRequestDTO(
            project.getProjectName(),
            project.getPosition(),
            project.getStartDate(),
            project.getEndDate(),
            project.getDescription()))
        .collect(Collectors.toList());

    // Mapping Applicant entity to DTO
    return ApplicantProfileResponseDTO.builder()
        .id(applicant.getId())
        .username(
            applicant.getUser().getUsername())
        .email(applicant.getUserEmail())
        .phoneNumber(
            applicant.getUser().getPhoneNumber())
        .description(applicant.getUser()
            .getDescription())
        .website(applicant.getUser().getWebsite())
        .firstname(applicant.getFirstname())
        .lastname(applicant.getLastname())
        .dob(applicant.getDob())
        .gender(applicant.getGender())
        .userEmail(applicant.getUserEmail())
        .position(applicant.getPosition())
        .objective(applicant.getObjective())
        .educationRequestDTO(educationDTOs)
        .workExperienceRequestDTO(workExperienceDTOs)
        .projectRequestDTO(projectDTOs)
        .skills(applicant.getSkills())
        .address(applicant.getUser().getAddresses().stream().findFirst()
            .map(Address::getAddressDescription).orElse(null))
        .build();
  }
}
