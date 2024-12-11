package vuluu.aggregationservice.dto.pageCustom;

import java.io.Serializable;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import vuluu.aggregationservice.dto.response.JobPostListResponseDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaginatedJobPostListResponseDTO implements Serializable {

  List<JobPostListResponseDTO> content;
  PageableDTO pageable;
  boolean last;
  int totalPages;
  long totalElements;
  int size;
  int number;
  SortDTO sort;
  boolean first;
  int numberOfElements;
  boolean empty;
}
