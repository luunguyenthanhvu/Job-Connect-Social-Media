package vuluu.websocketservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import vuluu.websocketservice.configuration.WebSocketHandler;
import vuluu.websocketservice.dto.request.UserRegisterRequestDTO;

@Controller
@RequiredArgsConstructor
public class NotificationController {

  private final WebSocketHandler webSocketHandler;

  @MessageMapping("/user.addUser")
  @SendTo("/user/topic")
  public void test(@Payload UserRegisterRequestDTO requestDTO,
      SimpMessageHeaderAccessor headerAccessor) {
    String userId = requestDTO.getUserId();  // Giả sử bạn có trường userId trong requestDTO
    String message = "User connected: " + userId;
    System.out.println("session id");
    System.out.println(headerAccessor.getSessionId());
    // Lưu userId vào WebSocketSession
    headerAccessor.getSessionAttributes().put("userId", userId);
    // Gửi thông báo đến tất cả các session của user
    webSocketHandler.sendNotification(userId, message);
  }
}
