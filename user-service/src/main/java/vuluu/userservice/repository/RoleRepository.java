package vuluu.userservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vuluu.userservice.entity.Role;
import vuluu.userservice.enums.ERole;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {

  @Query("SELECT r FROM Role r WHERE r.roleId = :roleId")
  Optional<Role> findByRoleId(@Param("roleId") ERole role);
}
