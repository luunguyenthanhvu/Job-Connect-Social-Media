package vuluu.userservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
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
public class AuthenticationRequestDTO implements Serializable {

  @Email
  String email;
  @Size(min = 6, message = "INVALID_PASSWORD")
  String password;
}
