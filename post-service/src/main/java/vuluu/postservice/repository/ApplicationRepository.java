package vuluu.postservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuluu.postservice.entity.Application;
import vuluu.postservice.entity.JobPost;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

  List<Application> findByJob(JobPost job);

  boolean existsByJobAndApplicantId(JobPost jobPost, String applicantId);

  void deleteApplicationByJobAndApplicantId(JobPost jobPost, String applicantId);
}
