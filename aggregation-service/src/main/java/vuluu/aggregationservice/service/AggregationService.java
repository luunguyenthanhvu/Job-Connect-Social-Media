package vuluu.aggregationservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.dto.response.ApiResponse;
import vuluu.aggregationservice.dto.response.UserResponseDTO;
import vuluu.aggregationservice.repository.UserClient;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AggregationService {

  UserClient userClient;

  public Mono<ApiResponse<UserResponseDTO>> getUserInfo() {
    return userClient.getUserInfo();
  }
}
