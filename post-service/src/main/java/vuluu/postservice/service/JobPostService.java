package vuluu.postservice.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vuluu.postservice.dto.request.JobApplyRequestDTO;
import vuluu.postservice.dto.request.JobPostRequestDTO;
import vuluu.postservice.dto.response.JobPostDetailResponseDTO;
import vuluu.postservice.dto.response.JobPostResponseDTO;
import vuluu.postservice.dto.response.MessageResponseDTO;
import vuluu.postservice.entity.Application;
import vuluu.postservice.entity.JobPost;
import vuluu.postservice.exception.AppException;
import vuluu.postservice.exception.ErrorCode;
import vuluu.postservice.mapper.JobPostMapper;
import vuluu.postservice.repository.ApplicationRepository;
import vuluu.postservice.repository.JobPostRepository;
import vuluu.postservice.util.MyUtils;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JobPostService {

  JobPostMapper jobPostMapper;
  JobPostRepository jobPostRepository;
  MyUtils myUtils;
  ApplicationRepository applicationRepository;

  @Transactional
  public JobPostResponseDTO postJob(JobPostRequestDTO requestDTO) {
    // get user Id from jwt token filter
    String userId = myUtils.getUserId();

    // map to jobEntity
    JobPost jobPost = jobPostMapper.toJobPost(requestDTO);
    jobPost.setUserId(userId);
    jobPostRepository.save(jobPost);

    return jobPostMapper.toJobPostResponseDTO(jobPost);
  }

  public JobPostDetailResponseDTO getJobDetail(Long jobId) {
    // get user Id
    String userId = myUtils.getUserId();

    var job = jobPostRepository.findById(jobId)
        .orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_EXISTED));

    // check if user applied this job
    boolean isApplied = applicationRepository.existsByJobAndApplicantId(job, userId);
    var response = jobPostMapper.toJobPostDetailResponseDTO(job);
    response.setApplied(isApplied);

    return jobPostMapper.toJobPostDetailResponseDTO(job);
  }

  public List<JobPostResponseDTO> getListCompanyJob(String userId) {
    List<JobPost> responseDTOS = jobPostRepository.findValidJobPostsByUserId(userId);

    return jobPostMapper.toListJobPostResponseDTO(responseDTOS);
  }

  @Transactional
  public MessageResponseDTO applyToJob(JobApplyRequestDTO requestDTO) {
    // get user Id
    String userId = myUtils.getUserId();

    var job = jobPostRepository.findById(requestDTO.getJobId())
        .orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_EXISTED));

    // check if user applied this job
    boolean isApplied = applicationRepository.existsByJobAndApplicantId(job, userId);

    // case user applied
    if (isApplied) {
      applicationRepository.deleteApplicationByJobAndApplicantId(job, userId);
      return new MessageResponseDTO("You have successfully canceled your application.");
    } else {
      // case user apply to new job
      Application application = Application
          .builder()
          .applicantId(userId)
          .job(job)
          .coverLetter(requestDTO.getCoverLetter())
          .build();

      applicationRepository.save(application);
      return new MessageResponseDTO("You have successfully applied to the job.");
    }
  }

}
