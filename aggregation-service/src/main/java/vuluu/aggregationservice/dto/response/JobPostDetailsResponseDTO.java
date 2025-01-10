package vuluu.aggregationservice.dto.response;

import java.io.Serializable;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import vuluu.aggregationservice.enums.EEmploymentType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JobPostDetailsResponseDTO implements Serializable {

  Long id;
  String title;
  String jobDescription;
  String jobExpertise;
  String jobWelfare;
  String userId;
  String addressId;
  EEmploymentType employmentType;
  int numberOfPositions;
  Date postedDate;
  Date expirationDate;
  boolean isApplied;
  String address;
  String username;
  String img;
}
