package vuluu.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuluu.postservice.entity.JobPost;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {

}
