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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EducationRequestDTO implements Serializable {

  String institutionName;
  String fieldOfStudy;
  String degree;
  Date startDate;
  Date endDate;
}
