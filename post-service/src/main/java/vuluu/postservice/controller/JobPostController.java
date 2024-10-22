package vuluu.postservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vuluu.postservice.dto.request.JobPostRequestDTO;
import vuluu.postservice.dto.response.ApiResponse;
import vuluu.postservice.dto.response.MessageResponseDTO;
import vuluu.postservice.service.JobPostService;

@RestController
@RequestMapping("/job-post")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobPostController {

  JobPostService jobPostService;

  @PostMapping("/create")
  public ApiResponse<MessageResponseDTO> postNewJob(@RequestBody JobPostRequestDTO requestDTO) {
    return ApiResponse.<MessageResponseDTO>builder().result(jobPostService.postJob(requestDTO))
        .build();
  }
}
