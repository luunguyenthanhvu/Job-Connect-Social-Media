package vuluu.postservice.dto.response;

import java.io.Serializable;
import java.util.Date;
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
public class JobPostListResponseDTO implements Serializable {

  Long id;
  String title;
  String userId; // employer id to get more info
  String addressId; // address id to get from employer
  Date postedDate;
}
