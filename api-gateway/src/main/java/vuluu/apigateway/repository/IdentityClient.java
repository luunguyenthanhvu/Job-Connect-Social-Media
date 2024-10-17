package vuluu.apigateway.repository;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;
import vuluu.apigateway.dto.request.IntrospectRequest;
import vuluu.apigateway.dto.response.ApiResponse;
import vuluu.apigateway.dto.response.IntrospectResponse;

public interface IdentityClient {
  @PostExchange(url = "/identity/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
  Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request);
}
