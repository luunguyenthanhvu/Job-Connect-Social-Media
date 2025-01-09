package vuluu.notificationservice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vuluu.notificationservice.dto.request.NotificationJobMatchingRequestDTO;
import vuluu.notificationservice.dto.response.JobSkillExtractResponseDTO;
import vuluu.notificationservice.dto.response.UserNotificationResponseDTO;
import vuluu.notificationservice.entity.Notification;
import vuluu.notificationservice.entity.UserNotification;
import vuluu.notificationservice.enums.EMessage;
import vuluu.notificationservice.enums.ETypeNotify;
import vuluu.notificationservice.repository.NotificationRepository;
import vuluu.notificationservice.repository.UserNotificationRepository;
import vuluu.notificationservice.service.kafka.NotificationProducerService;
import vuluu.notificationservice.util.MyUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationService {

  NotificationRepository notificationRepository;
  UserNotificationRepository userNotificationRepository;
  NotificationProducerService notificationProducerService;
  MyUtils myUtils;

  public List<UserNotificationResponseDTO> getNotificationsForUser() {
    String userId = myUtils.getUserId();
    List<UserNotification> data = userNotificationRepository.findByUserId(userId);
    List<UserNotificationResponseDTO> responseDTOS = new ArrayList<>();
    data.forEach(d -> {
      UserNotificationResponseDTO dto = UserNotificationResponseDTO
          .builder()
          .id(d.getNotification().getId())
          .createAt(d.getNotification().getCreateAt())
          .isRead(d.isRead())
          .fromId(d.getNotification().getFrom())
          .message(d.getNotification().getMessage())
          .postId(d.getNotification().getPostId())
          .type(d.getType())
          .build();

      responseDTOS.add(dto);
    });
    return responseDTOS;
  }

  public Integer countNotificationNotRead() {
    String userId = myUtils.getUserId();
    return userNotificationRepository.countUnreadNotifications(userId);
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
        .message(EMessage.SUGGEST_JOB.format(dto.getJobName()))
        .postId(dto.getJobId())
        .type(ETypeNotify.SUGGEST_JOB)
        .from(dto.getFromId())
        .createAt(time)
        .isGlobal(true)
        .build();

    notification = notificationRepository.save(notification);

    // 2. Tạo thông báo cho từng user trong matchingUsers
    List<UserNotification> notificationList = new ArrayList<>();
    for (String userId : dto.getMatchingUsers()) {
      UserNotification userNotification = UserNotification
          .builder()
          .userId(userId)
          .notification(notification)
          .type(notification.getType())
          .isRead(false)
          .build();
      notificationList.add(userNotification);
    }
    userNotificationRepository.saveAll(notificationList);
    // 3. Sau khi lưu dùng kafka gửi cho websocket service

    NotificationJobMatchingRequestDTO data =
        NotificationJobMatchingRequestDTO
            .builder()
            .id(notification.getId())
            .jobId(dto.getJobId())
            .message(notification.getMessage())
            .matchingUsers(dto.getMatchingUsers())
            .isRead(false)
            .build();

    notificationProducerService.notifyJobToUser(data);

  }


  public void sendGlobalNotification(Notification notification) {
    notification.setCreateAt(LocalDateTime.now());
    notificationRepository.save(notification);
  }
}

