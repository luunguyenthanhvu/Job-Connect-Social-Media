package vuluu.aggregationservice.dto.pageCustom;

import java.io.Serializable;
import java.util.List;
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
public class PageCustomResponseDTO<T> implements Serializable {

  List<T> content;
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
