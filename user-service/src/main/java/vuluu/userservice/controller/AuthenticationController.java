package vuluu.userservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vuluu.userservice.dto.request.AuthenticationRequestDTO;
import vuluu.userservice.dto.response.ApiResponse;
import vuluu.userservice.dto.response.AuthenticationResponseDTO;
import vuluu.userservice.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

  AuthenticationService authenticationService;

  @PostMapping("/token")
  ApiResponse<AuthenticationResponseDTO> authenticate(
      @RequestBody AuthenticationRequestDTO requestDTO) {
    return ApiResponse.<AuthenticationResponseDTO>builder()
        .result(authenticationService.authenticate(requestDTO))
        .build();
  }
}
