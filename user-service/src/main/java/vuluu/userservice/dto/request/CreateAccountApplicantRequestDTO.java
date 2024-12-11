package vuluu.userservice.dto.request;

import java.io.Serializable;
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
public class CreateAccountApplicantRequestDTO implements Serializable {


  byte[] img;// info for image profile
  String website; // info for website in user
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
