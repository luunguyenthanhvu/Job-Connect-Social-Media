package vuluu.postservice.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import vuluu.postservice.enums.EEmploymentType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostRequestDTO {

  String title;
  String jobDescription;
  String jobExpertise;
  String jobWelfare;
  String addressId;
  @Enumerated(value = EnumType.STRING)
  EEmploymentType employmentType;
  int numberOfPositions;
  Date expirationDate;
}
