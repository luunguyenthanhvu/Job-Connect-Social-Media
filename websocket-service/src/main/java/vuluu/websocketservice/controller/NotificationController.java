package vuluu.websocketservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import vuluu.websocketservice.dto.request.UserRegisterRequestDTO;

@Controller
@RequiredArgsConstructor
public class NotificationController {


  @MessageMapping("/user.addUser")
  @SendTo("/user/topic")
  public void test(@Payload UserRegisterRequestDTO requestDTO,
      SimpMessageHeaderAccessor headerAccessor) {
    String userId = requestDTO.getUserId();  // Giả sử bạn có trường userId trong requestDTO
    String message = "User connected: " + userId;
    headerAccessor.getSessionAttributes().put("userId", userId);

  }
}
