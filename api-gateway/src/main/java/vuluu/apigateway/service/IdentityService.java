package vuluu.apigateway.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import vuluu.apigateway.dto.request.IntrospectRequest;
import vuluu.apigateway.dto.response.ApiResponse;
import vuluu.apigateway.dto.response.IntrospectResponse;
import vuluu.apigateway.repository.IdentityClient;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityService {

  IdentityClient identityClient;

  public Mono<ApiResponse<IntrospectResponse>> introspect(String token) {
    return identityClient.introspect(IntrospectRequest.builder().token(token).build());
  }
}
