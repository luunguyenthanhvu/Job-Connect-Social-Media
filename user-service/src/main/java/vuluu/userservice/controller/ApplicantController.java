package vuluu.userservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vuluu.userservice.dto.request.CreateAccountApplicantRequestDTO;
import vuluu.userservice.dto.response.ApiResponse;
import vuluu.userservice.dto.response.MessageResponseDTO;
import vuluu.userservice.service.ApplicantService;

@RestController
@RequestMapping("/applicant")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicantController {

  ApplicantService applicantService;

  @PreAuthorize("hasRole('USER')")
  @PostMapping("/create")
  public ApiResponse<MessageResponseDTO> createApplicantAccount(@RequestBody
  CreateAccountApplicantRequestDTO requestDTO) {
    return ApiResponse.<MessageResponseDTO>builder()
        .result(applicantService.createApplicantAccount(requestDTO)).build();
  }
}
