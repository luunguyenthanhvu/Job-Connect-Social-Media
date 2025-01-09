package vuluu.aggregationservice.dto.response;

import java.util.Set;
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
public class UserResponseDTO {

  String id;
  String username;
  String email;
  String phoneNumber;
  boolean verified;
  String description;
  String img;
  Set<RoleResponseDTO> roles;
  Integer notifications;
}
