package vuluu.userservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vuluu.userservice.dto.response.ApiResponse;
import vuluu.userservice.dto.response.UserResponseDTO;
import vuluu.userservice.repository.UserRepository;
import vuluu.userservice.service.UserService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

  UserRepository userRepository;
  UserService userService;

  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  @GetMapping("/get-all")
  ApiResponse<Object> createUser() {
    return ApiResponse.<Object>builder()
        .result(userRepository.findAll()).build();
  }

  @PreAuthorize("hasRole('USER') or hasRole('EMPLOYER') or hasRole('ADMIN')")
  @GetMapping("/get-info")
  ApiResponse<UserResponseDTO> getUserInfo() {
    return ApiResponse.<UserResponseDTO>builder()
        .result(userService.getUser()).build();
  }

}
