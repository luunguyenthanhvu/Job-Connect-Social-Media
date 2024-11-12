package vuluu.aggregationservice.dto.response;

import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserBasicInfoResponseDTO {

  String id;
  String username;
  String email;
  Set<RoleResponseDTO> roles;
  String imageUrl;
}
