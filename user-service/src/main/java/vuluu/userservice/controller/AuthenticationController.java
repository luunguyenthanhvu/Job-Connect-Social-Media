package vuluu.userservice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vuluu.userservice.dto.request.AccountVerifyRequestDTO;
import vuluu.userservice.dto.request.AuthenticationRequestDTO;
import vuluu.userservice.dto.request.CreateAccountRequestDTO;
import vuluu.userservice.dto.response.ApiResponse;
import vuluu.userservice.dto.response.AuthenticationResponseDTO;
import vuluu.userservice.dto.response.MessageResponseDTO;
import vuluu.userservice.dto.response.UserResponseDTO;
import vuluu.userservice.service.AuthenticationService;
import vuluu.userservice.service.UserService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {

  AuthenticationService authenticationService;
  UserService userService;

  @PostMapping("/token")
  ApiResponse<AuthenticationResponseDTO> authenticate(
      @RequestBody AuthenticationRequestDTO requestDTO) {
    return ApiResponse.<AuthenticationResponseDTO>builder()
        .result(authenticationService.authenticate(requestDTO))
        .build();
  }

  @PostMapping("/registration")
  ApiResponse<UserResponseDTO> createUser(@RequestBody @Valid CreateAccountRequestDTO requestDTO) {
    return ApiResponse.<UserResponseDTO>builder()
        .result(userService.createUser(requestDTO)).build();
  }

  @PostMapping("/verify")
  ApiResponse<MessageResponseDTO> verifyAccount(
      @RequestBody @Valid AccountVerifyRequestDTO requestDTO) {
    return ApiResponse.<MessageResponseDTO>builder()
        .result(userService.verifyAccount(requestDTO)).build();
  }
}
