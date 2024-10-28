package vuluu.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuluu.postservice.entity.Application;
import vuluu.postservice.entity.JobPost;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

  boolean existsByJobAndApplicantId(JobPost jobPost, String applicantId);

  void deleteApplicationByJobAndApplicantId(JobPost jobPost, String applicantId);
}
