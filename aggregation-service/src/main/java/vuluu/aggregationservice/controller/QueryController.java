package vuluu.aggregationservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.dto.response.ApiResponse;
import vuluu.aggregationservice.dto.response.UserResponseDTO;
import vuluu.aggregationservice.service.UserAggregationService;

@RestController
@RequestMapping("/query")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class QueryController {

  UserAggregationService aggregationService;

  @GetMapping("/user-info")
  public Mono<ApiResponse<UserResponseDTO>> getUserById(
      @RequestParam("postId") String postId) {
    return aggregationService.getUserInfo(postId);
  }

}
