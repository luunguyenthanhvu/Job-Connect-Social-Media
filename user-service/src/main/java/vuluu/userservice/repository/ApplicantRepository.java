package vuluu.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuluu.userservice.entity.Applicant;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, String> {

}
