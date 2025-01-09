package vuluu.aggregationservice.controller;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.dto.pageCustom.PageCustomResponseDTO;
import vuluu.aggregationservice.dto.response.ApiResponse;
import vuluu.aggregationservice.dto.response.EnrichedJobPostResponseDTO;
import vuluu.aggregationservice.dto.response.ListUserNotificationResponseDTO;
import vuluu.aggregationservice.dto.response.NotifyListResponseDTO;
import vuluu.aggregationservice.dto.response.UserResponseDTO;
import vuluu.aggregationservice.service.NotificationAggregationService;
import vuluu.aggregationservice.service.PostAggregationService;
import vuluu.aggregationservice.service.UserAggregationService;

@RestController
@RequestMapping("/query")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class QueryController {

  UserAggregationService aggregationService;
  PostAggregationService postAggregationService;
  NotificationAggregationService notificationAggregationService;

  @GetMapping("/user-basic-info")
  public Mono<ApiResponse<UserResponseDTO>> getUserById(
      @RequestParam("postId") String postId) {
    return aggregationService.getUserInfo(postId);
  }

  @GetMapping("/job-list")
  public Mono<ApiResponse<PageCustomResponseDTO<EnrichedJobPostResponseDTO>>> getPageJobPost(
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {
    return postAggregationService.getPageJobPost(page, size);
  }

  @GetMapping("/notifications-list")
  public Mono<ApiResponse<List<ListUserNotificationResponseDTO>>> getNotificationList() {
    return notificationAggregationService.getUserNotifications();
  }

}
