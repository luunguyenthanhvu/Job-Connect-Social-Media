package vuluu.postservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vuluu.postservice.dto.request.JobPostRequestDTO;
import vuluu.postservice.dto.response.ApiResponse;
import vuluu.postservice.dto.response.JobPostDetailResponseDTO;
import vuluu.postservice.dto.response.JobPostResponseDTO;
import vuluu.postservice.service.JobPostService;

@RestController
@RequestMapping("/job-post")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobPostController {

  JobPostService jobPostService;

  @PostMapping("/create")
  @PreAuthorize("hasRole('EMPLOYER')")
  public ApiResponse<JobPostResponseDTO> postNewJob(@RequestBody JobPostRequestDTO requestDTO) {
    return ApiResponse.<JobPostResponseDTO>builder().result(jobPostService.postJob(requestDTO))
        .build();
  }

  @GetMapping("/get/job-detail/{id}")
  public ApiResponse<JobPostDetailResponseDTO> getJobPostDetails(@RequestHeader("id") Long id) {
    return null;
  }
}
