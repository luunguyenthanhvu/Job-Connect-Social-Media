package vuluu.postservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuluu.postservice.entity.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
