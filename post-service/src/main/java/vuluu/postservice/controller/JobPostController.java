package vuluu.postservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vuluu.postservice.dto.request.JobPostRequestDTO;
import vuluu.postservice.dto.response.ApiResponse;
import vuluu.postservice.dto.response.JobPostDetailResponseDTO;
import vuluu.postservice.dto.response.JobPostResponseDTO;
import vuluu.postservice.service.JobPostService;

@RestController
@RequestMapping("/job")
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

  @GetMapping("/get/job-detail/{jobId}")
  public ApiResponse<JobPostDetailResponseDTO> getJobPostDetail(
      @PathVariable Long jobId) {
    return ApiResponse.<JobPostDetailResponseDTO>builder().result(
        jobPostService.getJobDetail(jobId)).build();
  }

  @GetMapping("/get/company-job/{companyId}")
  public ApiResponse<JobPostResponseDTO> getListCompanyJob(
      @PathVariable Long companyId) {
    return null;
  }
}
