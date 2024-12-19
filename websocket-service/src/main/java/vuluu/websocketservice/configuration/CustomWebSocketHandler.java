package vuluu.websocketservice.configuration;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class CustomWebSocketHandler extends TextWebSocketHandler {

  @Override
  public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    super.afterConnectionEstablished(session);
    String sessionId = session.getId();  // Lấy session ID
    System.out.println("Client connected with sessionId: " + sessionId);

    // Xử lý khi kết nối WebSocket được mở
  }

  @Override
  public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    super.afterConnectionClosed(session, status);
    String sessionId = session.getId();  // Lấy session ID
    System.out.println("Client disconnected with sessionId: " + sessionId);

    // Xử lý khi kết nối WebSocket bị đóng
  }
}

