package vuluu.websocketservice.configuration;

import java.util.HashMap;
import java.util.Map;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketEventListener {

  // Lưu trữ các kết nối WebSocket theo userId
  private static Map<String, String> userSessions = new HashMap<>();

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    String sessionId = headerAccessor.getSessionId();
    System.out.println("Client connected with sessionId: " + sessionId);

    // Lấy userId từ header (được lấy qua interceptor hoặc từ header trực tiếp)
    String userId = getUserIdFromSession(headerAccessor);
    System.out.println("User ID: " + userId);

    if (userId != null) {
      // Lưu sessionId theo userId vào Map
      userSessions.put(userId, sessionId);
      System.out.println(
          "Client connected with sessionId: " + sessionId + " and userId: " + userId);
    }
  }

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    String sessionId = headerAccessor.getSessionId();
    System.out.println("Client disconnected with sessionId: " + sessionId);

    // Lấy userId từ header
    String userId = getUserIdFromSession(headerAccessor);

    if (userId != null) {
      // Xóa userId và sessionId khỏi Map khi kết nối bị ngắt
      userSessions.remove(userId);
      System.out.println("Removed userId " + userId + " from sessions");
    }
  }

  // Phương thức để lấy userId từ session (có thể từ header hoặc URL)
  private String getUserIdFromSession(StompHeaderAccessor headerAccessor) {
    // Lấy userId từ header (giả sử header chứa key "userId")
    return headerAccessor.getFirstNativeHeader("userId");
  }
}
