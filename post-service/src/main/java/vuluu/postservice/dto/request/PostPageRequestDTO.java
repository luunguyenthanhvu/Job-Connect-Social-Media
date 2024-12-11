package vuluu.postservice.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PostPageRequestDTO implements Pageable {

  Integer limit;
  Integer offset;
  final Sort sort;

  public PostPageRequestDTO(Integer limit, Integer offset) {
    this(limit, offset, Sort.unsorted());
  }

  @Override
  public int getPageNumber() {
    return offset / limit; // Số trang hiện tại
  }

  @Override
  public int getPageSize() {
    return limit; // Kích thước mỗi trang
  }

  @Override
  public long getOffset() {
    return offset; // Vị trí bắt đầu
  }

  @Override
  public Sort getSort() {
    return sort; // Thông tin sắp xếp
  }

  @Override
  public Pageable next() {
    return new PostPageRequestDTO(getPageSize(), (int) (getOffset() + getPageSize()));
  }

  public Pageable previous() {
    return hasPrevious() ? new PostPageRequestDTO(getPageSize(),
        (int) (getOffset() - getPageSize())) : this;
  }

  @Override
  public Pageable previousOrFirst() {
    return hasPrevious() ? previous() : first();
  }

  @Override
  public Pageable first() {
    return new PostPageRequestDTO(getPageSize(), 0);
  }

  @Override
  public Pageable withPage(int pageNumber) {
    return new PostPageRequestDTO(getPageSize(), pageNumber * getPageSize());
  }

  @Override
  public boolean hasPrevious() {
    return offset > limit;
  }
}
