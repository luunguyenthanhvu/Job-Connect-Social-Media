package vuluu.websocketservice.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

  @Override
  public void configureMessageBroker(MessageBrokerRegistry registry) {
    // Enable a simple message broker to carry the messages back to the client
    registry.enableSimpleBroker("/topic");  // prefix for outgoing messages
    registry.setApplicationDestinationPrefixes("/app");  // prefix for incoming messages
  }

  @Override
  public void registerStompEndpoints(StompEndpointRegistry registry) {
    registry.addEndpoint("/ws")
        .setAllowedOrigins(
            "http://localhost:3000") // Allowing specific origin for WebSocket connections
        .withSockJS();
  }

  @Override
  public void configureClientInboundChannel(ChannelRegistration registration) {
    registration.interceptors(rmeSessionChannelInterceptor());
  }

  @Bean
  public RmeSessionChannelInterceptor rmeSessionChannelInterceptor() {
    return new RmeSessionChannelInterceptor();
  }
}
