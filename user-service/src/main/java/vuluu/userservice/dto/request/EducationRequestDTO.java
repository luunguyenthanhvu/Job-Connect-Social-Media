package vuluu.userservice.dto.request;

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
public class EducationRequestDTO {

  String institutionName;
  String fieldOfStudy;
  String degree;
  Date startDate;
  Date endDate;
}
