package vuluu.aggregationservice.repository;

import java.util.List;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.dto.response.ApiResponse;
import vuluu.aggregationservice.dto.response.UserNotificationResponseDTO;

public interface NotificationClient {

  @GetExchange(url = "/notification-service/notifications/count-user-notifications")
  Mono<ApiResponse<Integer>> getUserNotification();

  @GetExchange(url = "/notification-service/notifications/user-notifications")
  Mono<ApiResponse<List<UserNotificationResponseDTO>>> getNotificationsForUser();


}
