package vuluu.postservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import vuluu.postservice.entity.JobPost;

@Repository
public interface JobPostPagingRepository extends PagingAndSortingRepository<JobPost, Long> {

  Page<JobPost> findAllBy(Pageable pageable);
}
