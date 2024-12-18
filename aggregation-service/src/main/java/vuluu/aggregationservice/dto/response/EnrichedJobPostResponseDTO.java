package vuluu.aggregationservice.dto.response;

import java.io.Serializable;
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
public class EnrichedJobPostResponseDTO implements Serializable {

  Long id;
  String title;
  String userId;
  String username;
  String addressId;
  String avatarUrl;
  String address;
  String postedDate;
}
