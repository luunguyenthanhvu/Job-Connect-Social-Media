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
import vuluu.aggregationservice.dto.response.UserBasicInfoResponseDTO;
import vuluu.aggregationservice.dto.response.UserResponseDTO;
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
    // Gọi API từ userWebClient để lấy thông tin người dùng
    Mono<ApiResponse<UserResponseDTO>> userMono = WebClientBuilder.createClient(uWebClient,
            UserClient.class)
        .getUserInfo();

    // Gọi API từ fileWebClient để lấy ảnh đại diện dựa trên userId
    Mono<ApiResponse<String>> userImage = WebClientBuilder.createClient(fWebClient,
        FileClient.class).getFileData("");

    // Kết hợp dữ liệu từ cả hai nguồn
    return Mono.zip(userMono, userImage)
        .map(tuple -> {
          UserResponseDTO user = tuple.getT1().getResult();
          String imageUrl = tuple.getT2().getResult();

          user.setImg(imageUrl);

          return new ApiResponse<>(200, "User info with image retrieved successfully", user);
        })
        .onErrorResume(e -> {
          log.error("Error fetching user info with image: ", e);
          return Mono.just(new ApiResponse<>(500, "Failed to retrieve user info with image", null));
        });
  }
}
