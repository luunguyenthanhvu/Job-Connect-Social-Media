package vuluu.websocketservice.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

  private final Map<String, List<String>> userSessions = new HashMap<>();
  private final SimpMessagingTemplate messagingTemplate;

  public WebSocketHandler(SimpMessagingTemplate messagingTemplate) {
    this.messagingTemplate = messagingTemplate;
  }

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    String userId = (String) session.getAttributes().get("userId"); // Lấy userId từ session
    if (userId != null) {
      userSessions.computeIfAbsent(userId, k -> new ArrayList<>()).add(session.getId());
    }
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    String userId = (String) session.getAttributes().get("userId");
    if (userId != null) {
      List<String> sessions = userSessions.get(userId);
      if (sessions != null) {
        sessions.remove(session.getId());
        if (sessions.isEmpty()) {
          userSessions.remove(userId);
        }
      }
    }
  }

  public void sendNotification(String userId, String message) {
    List<String> sessions = userSessions.get(userId);
    if (sessions != null) {
      for (String sessionId : sessions) {
        System.out.println("Gửi tin cho user");
        System.out.println(sessionId);
        messagingTemplate.convertAndSendToUser(sessionId, "/queue/notifications", message);
      }
    }
  }
}

