package vuluu.postservice.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vuluu.postservice.dto.response.ApplicantResponseDTO;
import vuluu.postservice.entity.Application;
import vuluu.postservice.entity.JobPost;
import vuluu.postservice.exception.AppException;
import vuluu.postservice.exception.ErrorCode;
import vuluu.postservice.repository.ApplicationRepository;
import vuluu.postservice.repository.JobPostRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationService {

  ApplicationRepository applicationRepository;
  JobPostRepository jobPostRepository;

  @Transactional(readOnly = true)
  public List<ApplicantResponseDTO> getApplicantsForJob(Long jobId) {
    log.error("Tìm kiếm công việc bằng post id");
    JobPost job = jobPostRepository.findById(jobId)
        .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    List<Application> applications = applicationRepository.findByJob(job);
    return applications.stream()
        .map(application -> ApplicantResponseDTO.builder()
            .applicationId(application.getId())
            .applicantId(application.getApplicantId())
            .coverLetter(application.getCoverLetter())
            .appliedDate(formatter.format(application.getAppliedDate()))
            .build())
        .collect(Collectors.toList());
  }

}

