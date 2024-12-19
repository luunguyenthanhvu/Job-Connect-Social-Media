package vuluu.websocketservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

@SpringBootApplication
@EnableDiscoveryClient
public class WebsocketServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebsocketServiceApplication.class, args);
  }

}
