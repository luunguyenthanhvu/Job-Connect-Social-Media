package vuluu.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuluu.userservice.entity.Employer;

@Repository
public interface EmployerRepository extends JpaRepository<Employer, String> {

}
