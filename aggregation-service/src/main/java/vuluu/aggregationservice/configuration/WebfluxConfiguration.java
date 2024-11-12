package vuluu.aggregationservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.WebFilter;


@Configuration
@Slf4j
public class WebfluxConfiguration implements WebFluxConfigurer {

  @Value("${server.servlet.context-path}")
  private String contextPath;

  @Bean
  public WebFilter contextPathWebFilter() {
    return (exchange, chain) -> {
      String path = exchange.getRequest().getURI().getPath();
      if (path.startsWith(contextPath)) {
        String newPath = path.substring(contextPath.length());
        return chain.filter(exchange.mutate()
            .request(exchange.getRequest().mutate().path(newPath).build())
            .build());
      }
      return chain.filter(exchange);
    };
  }

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



