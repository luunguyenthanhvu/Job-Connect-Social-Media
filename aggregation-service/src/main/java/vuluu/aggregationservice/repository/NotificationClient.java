package vuluu.aggregationservice.repository;

import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.dto.response.ApiResponse;

public interface NotificationClient {

  @GetExchange(url = "/notification-service/notifications/count-user-notifications")
  Mono<ApiResponse<Integer>> getUserNotification();
}
