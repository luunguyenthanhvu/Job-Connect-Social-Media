package vuluu.userservice.dto.request;

import jakarta.annotation.Nonnull;
import java.io.Serializable;
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
public class CreateAccountEmployerRequestDTO implements Serializable {

  byte[] img;
  @Nonnull
  String description;
  String[] address;
  String website;
  String country;
  String industry;
}
