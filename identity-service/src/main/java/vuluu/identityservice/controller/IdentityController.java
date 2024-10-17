package vuluu.identityservice.controller;

import com.nimbusds.jose.JOSEException;
import java.text.ParseException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vuluu.identityservice.dto.request.IntrospectRequest;
import vuluu.identityservice.dto.response.ApiResponse;
import vuluu.identityservice.dto.response.IntrospectResponse;
import vuluu.identityservice.service.IdentityService;

@RestController
@RequestMapping("/identity")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class IdentityController {

  IdentityService identityService;

  @PostMapping("/introspect")
  ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request)
      throws ParseException, JOSEException {
    var result = identityService.introspect(request);
    return ApiResponse.<IntrospectResponse>builder().result(result).build();
  }
}
