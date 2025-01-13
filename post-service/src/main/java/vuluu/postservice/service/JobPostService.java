package vuluu.postservice.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import vuluu.postservice.dto.request.ApplicantApplyJobRequestDTO;
import vuluu.postservice.dto.request.JobApplyRequestDTO;
import vuluu.postservice.dto.request.JobPostRequestDTO;
import vuluu.postservice.dto.request.JobSkillExtractRequestDTO;
import vuluu.postservice.dto.response.JobPostDetailResponseDTO;
import vuluu.postservice.dto.response.JobPostListResponseDTO;
import vuluu.postservice.dto.response.JobPostResponseDTO;
import vuluu.postservice.dto.response.JobSkillExtractResponseDTO;
import vuluu.postservice.dto.response.MessageResponseDTO;
import vuluu.postservice.entity.Application;
import vuluu.postservice.entity.JobPost;
import vuluu.postservice.exception.AppException;
import vuluu.postservice.exception.ErrorCode;
import vuluu.postservice.mapper.JobPostMapper;
import vuluu.postservice.repository.ApplicationRepository;
import vuluu.postservice.repository.JobPostPagingRepository;
import vuluu.postservice.repository.JobPostRepository;
import vuluu.postservice.service.kafka_producer.JobNotificationProducer;
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
  RestTemplate restTemplate;
  JobPostPagingRepository jobPostPagingRepository;
  JobNotificationProducer jobNotificationProducer;
  private final String targetUrl = "http://127.0.0.1:8090/extract_description";

  @Transactional
//  @CacheEvict(value = "jobPosts", allEntries = true)
  public JobPostResponseDTO postJob(JobPostRequestDTO requestDTO) {
    // get user Id from jwt token filter
    String userId = myUtils.getUserId();

    // map to jobEntity
    JobPost jobPost = jobPostMapper.toJobPost(requestDTO);
    jobPost.setUserId(userId);
    jobPost = jobPostRepository.save(jobPost);

    // post job data to extract service
    sendPostRequest(JobSkillExtractRequestDTO.builder()
        .jobId(jobPost.getId())
        .expirationDate(requestDTO.getExpirationDate())
        .jobDescription(jobPost.getJobExpertise())
        .build(), jobPost.getTitle(), userId);

    return jobPostMapper.toJobPostResponseDTO(jobPost);
  }

  //  @Cacheable(value = "jobPostCaches",
//      key = "'JobPost:' + #jobId", unless = "#result == null")
  public JobPostDetailResponseDTO getJobDetail(Long jobId) {
    System.out.println("In ra job id" + jobId);
    // get user Id
    String userId = myUtils.getUserId();

    var job = jobPostRepository.findById(jobId)
        .orElseThrow(() -> new AppException(ErrorCode.JOB_NOT_EXISTED));

    // check if user applied this job
    boolean isApplied = applicationRepository.existsByJobAndApplicantId(job, userId);

    var response = jobPostMapper.toJobPostDetailResponseDTO(job);
    response.setApplied(isApplied);  // Set the applied flag
    System.out.println(response);
    return response;  // Return the updated response object
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

      // send to kafka
      ApplicantApplyJobRequestDTO kafkaData = ApplicantApplyJobRequestDTO
          .builder()
          .jobId(job.getId())
          .fromId(userId) // from user Id
          .userId(job.getUserId()) // employer Id
          .jobName(job.getTitle())
          .build();
      jobNotificationProducer.notifyApplicantToEmployer(kafkaData);
      return new MessageResponseDTO("You have successfully applied to the job.");
    }
  }

  public void sendPostRequest(JobSkillExtractRequestDTO jobDTO, String jobName, String fromId) {
    JobSkillExtractResponseDTO response = restTemplate.postForObject(targetUrl, jobDTO,
        JobSkillExtractResponseDTO.class);
    // dùng kafka gửi thông báo
    response.setJobName(jobName);
    response.setFromId(fromId);
    jobNotificationProducer.notifyJobToUser(response);
    System.out.println("Đây là các response nhạn đuọc" + response);
    // In ra phản hồi nhận được
    if (response != null) {
      System.out.println("Phản hồi nhận được: " + response);
    } else {
      System.out.println("Không có phản hồi từ server");
    }
  }

  //  @Cacheable(value = "jobPosts", key = "'page:' + #page + ':size:' + #size")
  public Page<JobPostListResponseDTO> getJobPostPage(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<JobPost> jobPosts = jobPostPagingRepository.findAllBy(pageable);
    return jobPosts.map(jobPostMapper::toJobPostListResponseDTO);
  }
}
