package vuluu.userservice.controller;

import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vuluu.userservice.dto.request.AccountVerifyRequestDTO;
import vuluu.userservice.dto.request.CreateAccountRequestDTO;
import vuluu.userservice.dto.response.ApiResponse;
import vuluu.userservice.dto.response.MessageResponseDTO;
import vuluu.userservice.dto.response.UserResponseDTO;
import vuluu.userservice.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

  UserService userService;

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
