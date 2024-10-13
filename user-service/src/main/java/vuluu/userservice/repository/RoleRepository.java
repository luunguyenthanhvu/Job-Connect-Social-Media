package vuluu.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuluu.userservice.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

}
