package vuluu.userservice.dto.response;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import vuluu.userservice.dto.request.EducationRequestDTO;
import vuluu.userservice.dto.request.ProjectRequestDTO;
import vuluu.userservice.dto.request.WorkExperienceRequestDTO;
import vuluu.userservice.enums.EGender;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicantProfileResponseDTO implements Serializable {

  String id;
  String username;
  String email;
  String phoneNumber;
  String description;
  String img;
  String website;
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
  String address;
}
