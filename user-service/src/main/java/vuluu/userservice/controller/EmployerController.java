package vuluu.userservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vuluu.userservice.dto.request.CreateAccountEmployerRequestDTO;
import vuluu.userservice.dto.response.ApiResponse;
import vuluu.userservice.dto.response.MessageResponseDTO;
import vuluu.userservice.service.EmployerService;

@RestController
@RequestMapping("/employer")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmployerController {

  EmployerService employerService;

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/create")
  public ApiResponse<MessageResponseDTO> createEmployerAccount(
      @RequestBody CreateAccountEmployerRequestDTO requestDTO) {
    return ApiResponse.<MessageResponseDTO>builder()
        .result(employerService.createEmployerAccount(requestDTO)).build();
  }
}
