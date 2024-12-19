package vuluu.websocketservice.configuration;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Component
public class RmeSessionChannelInterceptor implements ChannelInterceptor {

  @Override
  public Message<?> preSend(Message<?> message, MessageChannel channel) {
    // Lấy các headers từ message
    MessageHeaders headers = message.getHeaders();
    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

    // Lấy MultiValueMap chứa các header gốc
    MultiValueMap<String, String> multiValueMap = headers.get(StompHeaderAccessor.NATIVE_HEADERS,
        MultiValueMap.class);

    // Lấy userId từ header
    String userId = accessor.getFirstNativeHeader("userId");

    // Kiểm tra và in ra userId nếu tồn tại
    if (userId != null) {
      System.out.println("Extracted userId: " + userId);
    } else {
      System.out.println("userId not found in headers");
    }

    return message;
  }
}
