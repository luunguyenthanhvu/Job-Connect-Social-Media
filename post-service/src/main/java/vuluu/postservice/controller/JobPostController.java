package vuluu.postservice.controller;

import java.util.List;
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
import vuluu.postservice.dto.request.JobApplyRequestDTO;
import vuluu.postservice.dto.request.JobPostRequestDTO;
import vuluu.postservice.dto.response.ApiResponse;
import vuluu.postservice.dto.response.JobPostDetailResponseDTO;
import vuluu.postservice.dto.response.JobPostResponseDTO;
import vuluu.postservice.dto.response.MessageResponseDTO;
import vuluu.postservice.service.JobPostService;

@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JobPostController {

  JobPostService jobPostService;

  @PreAuthorize("hasRole('EMPLOYER')")
  @PostMapping("/create")
  public ApiResponse<JobPostResponseDTO> postNewJob(@RequestBody JobPostRequestDTO requestDTO) {
    return ApiResponse.<JobPostResponseDTO>builder().result(jobPostService.postJob(requestDTO))
        .build();
  }

  @PreAuthorize("hasRole('EMPLOYER') or hasRole('USER')")
  @GetMapping("/get/job-detail/{jobId}")
  public ApiResponse<JobPostDetailResponseDTO> getJobPostDetail(@PathVariable Long jobId) {
    return ApiResponse.<JobPostDetailResponseDTO>builder()
        .result(jobPostService.getJobDetail(jobId)).build();
  }

  @PreAuthorize("hasRole('EMPLOYER') or hasRole('USER')")
  @GetMapping("/get/company-job/{companyId}")
  public ApiResponse<List<JobPostResponseDTO>> getListCompanyJob(@PathVariable String companyId) {
    return ApiResponse.<List<JobPostResponseDTO>>builder()
        .result(jobPostService.getListCompanyJob(companyId)).build();
  }

  @PostMapping("/apply")
  @PreAuthorize("hasRole('USER')")
  public ApiResponse<MessageResponseDTO> applyToJob(@RequestBody JobApplyRequestDTO requestDTO) {
    return ApiResponse.<MessageResponseDTO>builder().result(jobPostService.applyToJob(requestDTO))
        .build();
  }
}
