package vuluu.notificationservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import vuluu.notificationservice.dto.request.SendEmailRequestDTO;
import vuluu.notificationservice.dto.response.ApiResponse;
import vuluu.notificationservice.dto.response.EmailResponseDTO;
import vuluu.notificationservice.service.EmailService;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class EmailController {

  EmailService emailService;

  @PostMapping("/email/send")
  ApiResponse<EmailResponseDTO> sendEmail(@RequestBody SendEmailRequestDTO request) {
    return ApiResponse.<EmailResponseDTO>builder()
        .result(emailService.sendEmail(request))
        .build();
  }

}
