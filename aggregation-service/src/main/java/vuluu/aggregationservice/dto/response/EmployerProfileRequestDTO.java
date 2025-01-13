package vuluu.aggregationservice.dto.response;

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
public class EmployerProfileRequestDTO implements Serializable {

  String id;
  String username;
  String email;
  String img;
  String description;
  String[] address;
  String website;
  String country;
  String industry;
}
