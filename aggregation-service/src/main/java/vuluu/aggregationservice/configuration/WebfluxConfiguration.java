package vuluu.aggregationservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.server.WebFilter;

@Configuration
@Slf4j
public class WebfluxConfiguration {

  @Bean
  public WebFilter jwtWebFilter() {
    return (exchange, chain) -> {
      String jwt = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
      if (jwt != null && jwt.startsWith("Bearer ")) {
        log.info("Có jwt");
        log.info("jwt " + jwt);
        // Lưu JWT vào Reactor Context
        return chain.filter(exchange)
            .contextWrite(
                context -> context.put("jwt", jwt)); // Đảm bảo rằng JWT được thêm vào Context
      }
      log.error("không có jwt");
      return chain.filter(exchange); // Trả lại yêu cầu nếu không có JWT
    };
  }
}
