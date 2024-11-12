package vuluu.userservice.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuluu.userservice.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  @EntityGraph(attributePaths = {"roles.permissions"})
  Optional<User> findById(String id);

  boolean existsByEmail(String email);

  Optional<User> findByUsername(String username);

  Optional<User> findByEmail(String email);

}
