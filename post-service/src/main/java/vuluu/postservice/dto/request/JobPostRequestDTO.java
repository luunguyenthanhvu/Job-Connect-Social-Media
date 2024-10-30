package vuluu.postservice.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

  @NotNull
  @Size(min = 1, message = "Title cannot be empty")
  String title;

  @NotNull
  @Size(min = 1, message = "Description cannot be empty")
  String jobDescription;

  @NotNull
  @Size(min = 1, message = "Expertise cannot be empty")
  String jobExpertise;

  @NotNull
  @Size(min = 1, message = "Welfare cannot be empty")
  String jobWelfare;

  @NotNull(message = "Address ID cannot be null")
  String addressId;

  @Enumerated(value = EnumType.STRING)
  @NotNull(message = "Employment type cannot be null")
  EEmploymentType employmentType;

  @Min(value = 1, message = "Number of positions must be at least 1")
  int numberOfPositions;

  @NotNull(message = "Expiration date cannot be null")
  Date expirationDate;
}
