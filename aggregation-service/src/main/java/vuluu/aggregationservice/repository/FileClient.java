package vuluu.aggregationservice.repository;

import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.dto.response.ApiResponse;

public interface FileClient {

  @GetExchange(url = "/files")
  Mono<ApiResponse<Object>> getFileData();
}
