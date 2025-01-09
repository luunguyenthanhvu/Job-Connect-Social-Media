package vuluu.aggregationservice.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@Slf4j
public class WebClientConfiguration {

  @Value("${user-service.domain}")
  private String userServiceBaseURI;
  @Value("${post-service.domain}")
  private String postServiceBaseURI;
  @Value("${file-service.domain}")
  private String fileServiceBaseURI;
  @Value("${notification-service.domain}")
  private String notifyServiceBaseURI;

  @Bean
  @LoadBalanced
  public WebClient.Builder webClientBuilder() {
    log.info("Vào web client builder");
    return WebClient.builder()
        .filter(ExchangeFilterFunction.ofRequestProcessor(clientRequest ->
            Mono.deferContextual(contextView -> {
              // Lấy JWT từ Reactor Context
              String jwt = contextView.getOrDefault("jwt", null);
              log.info("có jwt");
              log.info("JWT in WebClient: " + jwt);
              if (jwt != null) {
                // Thêm JWT vào header Authorization của yêu cầu
                ClientRequest modifiedRequest = ClientRequest.from(clientRequest)
                    .header(HttpHeaders.AUTHORIZATION, jwt)
                    .build();
                return Mono.just(modifiedRequest);
              }
              log.info("Không có jwt");
              return Mono.just(clientRequest); // Trả về request gốc nếu không có JWT
            })
        ));
  }

  @Bean(value = "userWebClient")
  public WebClient userWebClient(WebClient.Builder builder) {
    return createWebClient(builder, userServiceBaseURI);
  }

  @Bean(value = "postWebClient")
  public WebClient postWebClient(WebClient.Builder builder) {
    return createWebClient(builder, postServiceBaseURI);
  }

  @Bean(value = "fileWebClient")
  public WebClient fileWebClient(WebClient.Builder builder) {
    return createWebClient(builder, fileServiceBaseURI);
  }

  @Bean(value = "notifyWebClient")
  public WebClient notifyWebClient(WebClient.Builder builder) {
    return createWebClient(builder, notifyServiceBaseURI);
  }

  private WebClient createWebClient(WebClient.Builder builder, String baseURI) {
    return builder
        .baseUrl(baseURI) // Thiết lập base URL cho WebClient
        .build();
  }
}
