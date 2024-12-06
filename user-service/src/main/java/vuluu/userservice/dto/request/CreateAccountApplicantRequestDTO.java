package vuluu.userservice.dto.request;

import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import vuluu.userservice.enums.EGender;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccountApplicantRequestDTO {

  String firstname;
  String lastname;
  Date dob;
  EGender gender;
  String userEmail;
  String position;
  String objective;
  List<EducationRequestDTO> educationRequestDTO;
  List<WorkExperienceRequestDTO> workExperienceRequestDTO;
  List<ProjectRequestDTO> projectRequestDTO;
  String skills;
  String[] address;
}
