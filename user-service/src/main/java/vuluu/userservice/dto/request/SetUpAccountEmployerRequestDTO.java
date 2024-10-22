package vuluu.userservice.dto.request;

import jakarta.annotation.Nonnull;
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
public class SetUpAccountEmployerRequestDTO {

  @Nonnull
  String description;
  String[] address;
  String website;
  String country;
  String industry;
}
