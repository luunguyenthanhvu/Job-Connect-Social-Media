package vuluu.aggregationservice.repository;

import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.dto.response.ApiResponse;
import vuluu.aggregationservice.dto.response.UserResponseDTO;

public interface UserClient {

  @GetExchange(url = "/users/get-info")
  Mono<ApiResponse<UserResponseDTO>> getUserInfo();
}
