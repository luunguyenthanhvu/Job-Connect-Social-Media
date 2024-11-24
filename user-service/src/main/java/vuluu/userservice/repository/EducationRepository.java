package vuluu.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuluu.userservice.entity.Education;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long> {

}
