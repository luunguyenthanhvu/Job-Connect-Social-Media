package vuluu.userservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuluu.userservice.entity.Address;
import vuluu.userservice.entity.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

  List<Address> findAllByUser(User user);
}
