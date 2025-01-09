package vuluu.notificationservice.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import vuluu.notificationservice.entity.UserNotification;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification, Long> {

  // Lấy thông báo theo userId
  List<UserNotification> findByUserId(String userId);

  // Lấy thông báo chưa đọc theo userId
  List<UserNotification> findByUserIdAndIsReadFalse(String userId);

  @Query("SELECT COUNT(un) FROM user_notification un WHERE un.userId = :userId AND un.isRead = false")
  Integer countUnreadNotifications(@Param("userId") String userId);
}
