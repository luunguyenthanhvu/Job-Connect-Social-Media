package vuluu.websocketservice.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
        .allowedOrigins("http://localhost:3000",
            "http://localhost:8888", "http://localhost:8888/api") // Allow the specific frontend origin
        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow these methods
        .allowCredentials(true); // Allow credentials (cookies, etc.)
  }
}
