package vuluu.userservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuluu.userservice.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
