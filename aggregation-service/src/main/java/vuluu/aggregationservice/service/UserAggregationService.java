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
import vuluu.aggregationservice.exception.AppException;
import vuluu.aggregationservice.exception.ErrorCode;
import vuluu.aggregationservice.repository.FileClient;
import vuluu.aggregationservice.repository.UserClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAggregationService {

  private WebClient uWebClient;
  private WebClient fWebClient;


  @Autowired
  private void setUserWebClient(@Qualifier("userWebClient") WebClient webClient) {
    this.uWebClient = webClient;
  }

  @Autowired
  private void setFileWebClient(@Qualifier("fileWebClient") WebClient webClient) {
    this.fWebClient = webClient;
  }

  public Mono<ApiResponse<UserResponseDTO>> getUserInfo(String postId) {
    Mono<ApiResponse<UserResponseDTO>> userMono = WebClientBuilder.createClient(uWebClient,
            UserClient.class)
        .getUserInfo()
        .doOnError(e -> log.error("Error occurred in user service: ", e));

    Mono<ApiResponse<String>> userImage = WebClientBuilder.createClient(fWebClient,
            FileClient.class)
        .getFileData("")
        .doOnError(e -> log.error("Error occurred in file service: ", e));

    return Mono.zip(userMono, userImage)
        .flatMap(tuple -> {
          UserResponseDTO user = tuple.getT1().getResult();
          String imageUrl = tuple.getT2().getResult();

          user.setImg(imageUrl);

          return Mono.just(
              new ApiResponse<>(200, "User info with image retrieved successfully", user));
        })
        .onErrorResume(e -> {
          if (e instanceof AppException) {
            log.error("AppException occurred: ", e);
            return Mono.just(new ApiResponse<>(500, e.getMessage(), null));
          } else {
            log.error("General error occurred: ", e);
            return Mono.just(
                new ApiResponse<>(ErrorCode.USER_NOT_CHOSE_TYPE.getCode(),
                    ErrorCode.USER_NOT_CHOSE_TYPE.getMessage(),
                    null));
          }
        });
  }
}
