package vuluu.aggregationservice.dto.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import vuluu.aggregationservice.enums.ETypeNotify;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserNotificationResponseDTO implements Serializable {

  Long id;
  String message;
  ETypeNotify type;
  LocalDateTime createAt;
  String fromId;
  String postId;
  boolean isRead;
}
