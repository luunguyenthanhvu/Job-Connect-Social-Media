package vuluu.aggregationservice.repository;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.dto.response.ApiResponse;

public interface FileClient {

  @GetExchange(url = "/file-service/image/search")
  Mono<ApiResponse<String>> getFileData(@RequestParam("postId") String postId);
}
