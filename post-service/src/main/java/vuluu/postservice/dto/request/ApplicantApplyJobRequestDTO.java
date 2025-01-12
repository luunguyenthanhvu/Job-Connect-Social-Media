package vuluu.postservice.dto.request;

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
public class ApplicantApplyJobRequestDTO implements Serializable {

  Long jobId;
  String fromId; // applicant id
  String userId;
  String jobName;
}
