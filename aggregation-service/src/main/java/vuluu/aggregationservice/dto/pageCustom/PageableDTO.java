package vuluu.aggregationservice.dto.pageCustom;

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
public class PageableDTO {

  int pageNumber;
  int pageSize;
  SortDTO sort;
  int offset;
  boolean paged;
  boolean unpaged;
}
