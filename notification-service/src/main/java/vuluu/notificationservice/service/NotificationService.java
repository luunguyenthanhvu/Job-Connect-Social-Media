package vuluu.notificationservice.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vuluu.notificationservice.entity.Notification;
import vuluu.notificationservice.entity.UserNotification;
import vuluu.notificationservice.repository.NotificationRepository;
import vuluu.notificationservice.repository.UserNotificationRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationService {

  NotificationRepository notificationRepository;
  UserNotificationRepository userNotificationRepository;


  public List<UserNotification> getNotificationsForUser(String userId) {
    return userNotificationRepository.findByUserId(userId);
  }


  public void markAsRead(Long userNotificationId) {
    UserNotification userNotification = userNotificationRepository.findById(userNotificationId)
        .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
    userNotification.setReadAt(LocalDateTime.now());
    userNotification.setRead(true);
    userNotificationRepository.save(userNotification);
  }


  public void sendNotificationToUser(String userId, Notification notification) {
    notification.setCreateAt(LocalDateTime.now());
    notificationRepository.save(notification);

    UserNotification userNotification = UserNotification.builder()
        .userId(userId)
        .notification(notification)
        .type(notification.getType())
        .isRead(false)
        .build();

    userNotificationRepository.save(userNotification);
  }


  public void sendGlobalNotification(Notification notification) {
    notification.setCreateAt(LocalDateTime.now());
    notification.setGlobal(true);
    notificationRepository.save(notification);

    // Logic để gửi thông báo đến tất cả người dùng (nếu cần)
  }
}

