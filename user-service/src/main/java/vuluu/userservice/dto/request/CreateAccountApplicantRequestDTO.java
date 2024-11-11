package vuluu.userservice.dto.request;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateAccountApplicantRequestDTO {

  String firstname;
  String lastname;
  Date dob;
  String summary;
  String educationList;
  String workExperiences;
  String skills;
  String certifications;
  String[] address;
}
