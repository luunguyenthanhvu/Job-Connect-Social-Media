package vuluu.aggregationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.configuration.WebClientBuilder;
import vuluu.aggregationservice.dto.response.ApiResponse;
import vuluu.aggregationservice.dto.response.UserResponseDTO;
import vuluu.aggregationservice.repository.UserClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAggregationService {

  private WebClient uWebClient;

  @Autowired
  private void setUserWebClient(@Qualifier("userWebClient") WebClient webClient) {
    this.uWebClient = webClient;
  }

  public Mono<ApiResponse<UserResponseDTO>> getUserInfo() {
    var userWebClient = WebClientBuilder.createClient(uWebClient, UserClient.class);
    return userWebClient.getUserInfo();
  }
}
