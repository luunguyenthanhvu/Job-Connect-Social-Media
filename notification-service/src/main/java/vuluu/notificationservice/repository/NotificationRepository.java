package vuluu.notificationservice.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vuluu.notificationservice.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
