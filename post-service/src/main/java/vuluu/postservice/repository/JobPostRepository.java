package vuluu.postservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vuluu.postservice.entity.JobPost;

@Repository
public interface JobPostRepository extends JpaRepository<JobPost, Long> {

  @Query("SELECT jp FROM job_post jp WHERE jp.userId = :userId AND jp.expirationDate >= CURRENT_DATE")
  List<JobPost> findValidJobPostsByUserId(@Param("userId") String userId);
}
