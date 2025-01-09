package vuluu.websocketservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  /**
   * Cấu hình message broker trong Spring WebSocket. Phương thức này thiết lập các cài đặt liên quan
   * đến message broker và định tuyến các thông điệp để gửi tới các client qua WebSocket.
   *
   * @param registry đối tượng MessageBrokerRegistry dùng để cấu hình message broker.
   */
  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    registry.enableSimpleBroker("/user");
    registry.setApplicationDestinationPrefixes("/app");
    registry.setUserDestinationPrefix("/user");
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws")
        .setAllowedOrigins(
            "http://localhost:3000", "http://localhost:8888")
        .withSockJS();
  }

  @Override
  public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
    DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
    resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);

    MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
    converter.setObjectMapper(new ObjectMapper());
    converter.setContentTypeResolver(resolver);
    messageConverters.add(new StringMessageConverter());
    messageConverters.add(new ByteArrayMessageConverter());
    messageConverters.add(converter);

    // if return true the message converters will use default converter (-_-)
    return false;
  }
}
