package vuluu.postservice.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vuluu.postservice.dto.request.JobPostRequestDTO;
import vuluu.postservice.dto.response.MessageResponseDTO;
import vuluu.postservice.entity.JobPost;
import vuluu.postservice.mapper.JobPostMapper;
import vuluu.postservice.repository.JobPostRepository;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class JobPostService {

  JobPostMapper jobPostMapper;
  JobPostRepository jobPostRepository;

  @Transactional
  public MessageResponseDTO postJob(JobPostRequestDTO requestDTO) {
    // get user Id from jwt token filter
    String userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    JobPost jobPost = jobPostMapper.toJobPost(requestDTO);
    jobPost.setUserId(userId);
    jobPostRepository.save(jobPost);
    return MessageResponseDTO.builder().message("Post Job success!").build();
  }
}
