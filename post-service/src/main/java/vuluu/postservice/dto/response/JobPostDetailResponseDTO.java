package vuluu.postservice.dto.response;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import vuluu.postservice.enums.EEmploymentType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostDetailResponseDTO {

  Long id;
  String title;
  String jobDescription;
  String jobExpertise;
  String jobWelfare;
  String userId;
  String addressId;
  @Enumerated(value = EnumType.STRING)
  EEmploymentType employmentType;
  int numberOfPositions;
  Date postedDate;
  Date expirationDate;
}
