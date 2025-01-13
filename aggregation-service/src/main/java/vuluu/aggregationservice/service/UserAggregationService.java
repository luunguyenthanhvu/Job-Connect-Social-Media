package vuluu.aggregationservice.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.configuration.WebClientBuilder;
import vuluu.aggregationservice.dto.request.ListUserGetImgRequestDTO;
import vuluu.aggregationservice.dto.response.ApiResponse;
import vuluu.aggregationservice.dto.response.ApplicantProfileResponseDTO;
import vuluu.aggregationservice.dto.response.EmployerProfileRequestDTO;
import vuluu.aggregationservice.dto.response.ListUserWithImgResponseDTO;
import vuluu.aggregationservice.dto.response.UserResponseDTO;
import vuluu.aggregationservice.exception.AppException;
import vuluu.aggregationservice.exception.ErrorCode;
import vuluu.aggregationservice.repository.FileClient;
import vuluu.aggregationservice.repository.NotificationClient;
import vuluu.aggregationservice.repository.UserClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserAggregationService {

  private WebClient uWebClient;
  private WebClient fWebClient;
  private WebClient nWebClient;


  @Autowired
  private void setUserWebClient(@Qualifier("userWebClient") WebClient webClient) {
    this.uWebClient = webClient;
  }

  @Autowired
  private void setFileWebClient(@Qualifier("fileWebClient") WebClient webClient) {
    this.fWebClient = webClient;
  }

  @Autowired
  private void setNotificationWebClient(@Qualifier("notifyWebClient") WebClient webClient) {
    this.nWebClient = webClient;
  }

  public Mono<ApiResponse<UserResponseDTO>> getUserInfo(String postId) {
    // Gọi API từ userWebClient để lấy thông tin người dùng
    Mono<ApiResponse<UserResponseDTO>> userMono = WebClientBuilder.createClient(uWebClient,
            UserClient.class)
        .getUserInfo();

    // Gọi API từ fileWebClient để lấy ảnh đại diện dựa trên userId
    Mono<ApiResponse<String>> userImage = WebClientBuilder.createClient(fWebClient,
        FileClient.class).getFileData("");

    Mono<ApiResponse<Integer>> notifications = WebClientBuilder.createClient(nWebClient,
        NotificationClient.class).getUserNotification();

    // Kết hợp dữ liệu từ cả hai nguồn
    return Mono.zip(userMono, userImage, notifications)
        .map(tuple -> {
          UserResponseDTO user = tuple.getT1().getResult();
          String imageUrl = tuple.getT2().getResult();

          user.setImg(imageUrl);
          user.setNotifications(tuple.getT3().getResult());

          return new ApiResponse<>(200, "User info with image retrieved successfully", user);
        })
        .onErrorResume(e -> {
          log.error("Error fetching user info with image: ", e);
          return Mono.error(new AppException(ErrorCode.USER_NOT_CHOSE_TYPE));
        });
  }

  public Mono<ApiResponse<ApplicantProfileResponseDTO>> getApplicantProfile(String id) {
    Mono<ApiResponse<ApplicantProfileResponseDTO>> userMono = WebClientBuilder.createClient(
            uWebClient,
            UserClient.class)
        .getApplicantProfile(id);

    return userMono.flatMap(data -> {
      ApplicantProfileResponseDTO applicantProfileResponseDTO = data.getResult();

      // Chuẩn bị dữ liệu request cho từng service
      List<ListUserGetImgRequestDTO> userImgRequests = new ArrayList<>();
      ListUserGetImgRequestDTO userImage = ListUserGetImgRequestDTO.builder()
          .userId(applicantProfileResponseDTO.getId())
          .postId(applicantProfileResponseDTO.getId())
          .build();
      userImgRequests.add(userImage);

      // Gọi API batch
      Mono<List<ListUserWithImgResponseDTO>> userImagesMono = WebClientBuilder.createClient(
              fWebClient, FileClient.class)
          .getUserImage(userImgRequests)
          .map(ApiResponse::getResult);

      return userImagesMono.map(userImages -> {
        // Kết hợp kết quả ảnh vào đối tượng ApplicantProfileResponseDTO
        if (!userImages.isEmpty()) {
          ListUserWithImgResponseDTO userImageResponse = userImages.get(0);
          applicantProfileResponseDTO.setImg(userImageResponse.getImg());
        }
        return ApiResponse.<ApplicantProfileResponseDTO>builder()
            .result(applicantProfileResponseDTO)
            .build();
      });
    });
  }

  public Mono<ApiResponse<EmployerProfileRequestDTO>> getEmployerProfile(String id) {
    Mono<ApiResponse<EmployerProfileRequestDTO>> userMono = WebClientBuilder.createClient(
            uWebClient,
            UserClient.class)
        .getEmployerProfile(id);

    return userMono.flatMap(data -> {
      EmployerProfileRequestDTO applicantProfileResponseDTO = data.getResult();

      // Chuẩn bị dữ liệu request cho từng service
      List<ListUserGetImgRequestDTO> userImgRequests = new ArrayList<>();
      ListUserGetImgRequestDTO userImage = ListUserGetImgRequestDTO.builder()
          .userId(applicantProfileResponseDTO.getId())
          .postId(applicantProfileResponseDTO.getId())
          .build();
      userImgRequests.add(userImage);

      // Gọi API batch
      Mono<List<ListUserWithImgResponseDTO>> userImagesMono = WebClientBuilder.createClient(
              fWebClient, FileClient.class)
          .getUserImage(userImgRequests)
          .map(ApiResponse::getResult);

      return userImagesMono.map(userImages -> {
        // Kết hợp kết quả ảnh vào đối tượng ApplicantProfileResponseDTO
        if (!userImages.isEmpty()) {
          ListUserWithImgResponseDTO userImageResponse = userImages.get(0);
          applicantProfileResponseDTO.setImg(userImageResponse.getImg());
        }
        return ApiResponse.<EmployerProfileRequestDTO>builder()
            .result(applicantProfileResponseDTO)
            .build();
      });
    });
  }
}

