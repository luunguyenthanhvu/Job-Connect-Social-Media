package vuluu.websocketservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class NotificationController {

  private final SimpMessagingTemplate messagingTemplate;

  @PostMapping("/send-notification")
  public void sendNotification(@RequestBody String message) {
    // Gửi thông báo đến tất cả các client đang kết nối với topic /topic/notifications
    messagingTemplate.convertAndSend("/topic/notifications", message);
  }
}
