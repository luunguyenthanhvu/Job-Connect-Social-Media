package vuluu.postservice.dto.response;

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
public class JobPostResponseDTO {

  String title;
  @Enumerated(value = EnumType.STRING)
  EEmploymentType employmentType;
  int numberOfPositions;
  Date postedDate;
  Date expirationDate;
}
