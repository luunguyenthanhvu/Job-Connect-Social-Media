package vuluu.notificationservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vuluu.notificationservice.dto.request.NotifyJobSkillExtractResponseDTO;
import vuluu.notificationservice.dto.response.JobSkillExtractResponseDTO;
import vuluu.notificationservice.dto.response.UserNotificationResponseDTO;
import vuluu.notificationservice.entity.Notification;
import vuluu.notificationservice.entity.UserNotification;
import vuluu.notificationservice.enums.EMessage;
import vuluu.notificationservice.enums.ETypeNotify;
import vuluu.notificationservice.repository.NotificationRepository;
import vuluu.notificationservice.repository.UserNotificationRepository;
import vuluu.notificationservice.service.kafka.NotificationProducerService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationService {

  NotificationRepository notificationRepository;
  UserNotificationRepository userNotificationRepository;
  NotificationProducerService notificationProducerService;

  public List<UserNotificationResponseDTO> getNotificationsForUser(String userId) {
    List<UserNotification> data = userNotificationRepository.findByUserId(userId);
    List<UserNotificationResponseDTO> responseDTOS = new ArrayList<>();
    data.forEach(d -> {
      UserNotificationResponseDTO dto = UserNotificationResponseDTO
          .builder()
          .id(d.getNotification().getId())
          .createAt(d.getNotification().getCreateAt())
          .isRead(d.isRead())
          .from(d.getNotification().getFrom())
          .message(d.getNotification().getMessage())
          .postId(d.getNotification().getPostId())
          .type(d.getType())
          .build();

      responseDTOS.add(dto);
    });
    return responseDTOS;
  }


  public void markAsRead(Long userNotificationId) {
    UserNotification userNotification = userNotificationRepository.findById(userNotificationId)
        .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
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

  public void sendSuggestJobToUsers(JobSkillExtractResponseDTO dto) {
    LocalDateTime time = LocalDateTime.now();

    // 1. Lưu thông báo chung vào bảng Notification
    Notification notification = Notification
        .builder()
        .message(EMessage.SUGGEST_JOB)
        .postId(dto.getJobId())
        .type(ETypeNotify.SUGGEST_JOB)
        .createAt(time)
        .isGlobal(true)
        .build();

    notification = notificationRepository.save(notification);

    // 2. Tạo thông báo cho từng user trong matchingUsers
    List<String> notificationList = new ArrayList<>();
    for (String userId : dto.getMatchingUsers()) {
      UserNotification userNotification = UserNotification
          .builder()
          .userId(userId)
          .notification(notification)
          .type(notification.getType())
          .isRead(false)
          .build();

      userNotificationRepository.save(userNotification);
    }

    // 3. Sau khi lưu dùng kafka gửi cho websocket service
    NotifyJobSkillExtractResponseDTO data =
        NotifyJobSkillExtractResponseDTO
            .builder()
            .id(notification.getId())
            .jobId(dto.getJobId())
            .matchingUsers(dto.getMatchingUsers())
            .build();
    notificationProducerService.notifyJobToUser(data);

  }


  public void sendGlobalNotification(Notification notification) {
    notification.setCreateAt(LocalDateTime.now());
    notification.setGlobal(true);
    notificationRepository.save(notification);
  }
}

