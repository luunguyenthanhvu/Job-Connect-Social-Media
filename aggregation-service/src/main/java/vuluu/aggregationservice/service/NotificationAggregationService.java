package vuluu.aggregationservice.service;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.configuration.WebClientBuilder;
import vuluu.aggregationservice.dto.request.ListUserGetImgRequestDTO;
import vuluu.aggregationservice.dto.request.ListUserNameRequestDTO;
import vuluu.aggregationservice.dto.response.ApiResponse;
import vuluu.aggregationservice.dto.response.ListUserNotificationResponseDTO;
import vuluu.aggregationservice.dto.response.ListUserWithImgResponseDTO;
import vuluu.aggregationservice.dto.response.UserNameWithPostResponseDTO;
import vuluu.aggregationservice.dto.response.UserNotificationResponseDTO;
import vuluu.aggregationservice.repository.FileClient;
import vuluu.aggregationservice.repository.NotificationClient;
import vuluu.aggregationservice.repository.UserClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationAggregationService {

  private WebClient nWebClient;
  private WebClient uWebClient;
  private WebClient fWebClient;

  @Autowired
  private void setNotifyWebClient(@Qualifier("notifyWebClient") WebClient webClient) {
    this.nWebClient = webClient;
  }

  @Autowired
  private void setUserWebClient(@Qualifier("userWebClient") WebClient webClient) {
    this.uWebClient = webClient;
  }

  @Autowired
  private void setFileWebClient(@Qualifier("fileWebClient") WebClient webClient) {
    this.fWebClient = webClient;
  }

  public Mono<ApiResponse<List<ListUserNotificationResponseDTO>>> getUserNotifications() {
    Mono<ApiResponse<List<UserNotificationResponseDTO>>> listNotifications = WebClientBuilder.createClient(
        nWebClient, NotificationClient.class).getNotificationsForUser();

    return listNotifications.flatMap(listApiResponse -> {
      if (listApiResponse != null && listApiResponse.getResult() != null) {
        List<UserNotificationResponseDTO> notifications = listApiResponse.getResult();
        if (notifications == null || notifications.isEmpty()) {
          return Mono.just(new ApiResponse<>());
        }

        System.out.println(notifications);
        // Chuẩn bị dữ liệu request cho từng services
        List<ListUserGetImgRequestDTO> userImgRequests = notifications.stream()
            .map(notification -> new ListUserGetImgRequestDTO(notification.getId().toString(),
                notification.getFromId()))
            .collect(Collectors.toList());

        List<ListUserNameRequestDTO> userNameRequests = notifications.stream()
            .map(notification -> new ListUserNameRequestDTO(notification.getId().toString(),
                notification.getFromId()))
            .collect(Collectors.toList());

        // Gọi API batch
        Mono<List<ListUserWithImgResponseDTO>> userImagesMono = WebClientBuilder.createClient(
                fWebClient, FileClient.class)
            .getUserImage(userImgRequests)
            .map(ApiResponse::getResult);

        Mono<List<UserNameWithPostResponseDTO>> userNameMono = WebClientBuilder.createClient(
                uWebClient, UserClient.class)
            .getUserNameWithPost(userNameRequests)
            .map(ApiResponse::getResult);

        return Mono.zip(userImagesMono, userNameMono).map(tuple -> {
          List<ListUserWithImgResponseDTO> userImages = tuple.getT1();
          List<UserNameWithPostResponseDTO> userNames = tuple.getT2();

          Map<String, String> userImageMap = userImages.stream()
              .collect(Collectors.toMap(ListUserWithImgResponseDTO::getPostId,
                  ListUserWithImgResponseDTO::getImg));


          Map<String, UserNameWithPostResponseDTO> userNameMap =
              userNames.stream()
                  .collect(Collectors.toMap(UserNameWithPostResponseDTO::getPostId, info -> info));

          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
          List<ListUserNotificationResponseDTO> enrichedNotifications = notifications.stream()
              .map(data -> {
                return ListUserNotificationResponseDTO
                    .builder()
                    .id(data.getId())
                    .message(data.getMessage())
                    .type(data.getType())
                    .createAt(formatter.format(data.getCreateAt()))
                    .fromId(data.getFromId())
                    .postId(data.getPostId())
                    .isRead(data.isRead())
                    .userName(userNameMap.get(String.valueOf(data.getId())).getUsername())
                    .userImg(userImageMap.get(String.valueOf(data.getId())))
                    .build();
              })
              .sorted(Comparator.comparing(ListUserNotificationResponseDTO::getId).reversed())
              .collect(Collectors.toList());

          // Trả về kết quả bao bọc trong ApiResponse
          return ApiResponse.<List<ListUserNotificationResponseDTO>>builder()
              .result(enrichedNotifications).build();
        });
      } else {
        return Mono.just(new ApiResponse<>());
      }
    });
  }

}
