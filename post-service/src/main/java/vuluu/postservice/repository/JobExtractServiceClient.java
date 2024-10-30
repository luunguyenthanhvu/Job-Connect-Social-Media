package vuluu.postservice.repository;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;
import vuluu.postservice.dto.request.JobSkillExtractRequestDTO;
import vuluu.postservice.dto.request.UserSkillExtractRequestDTO;
import vuluu.postservice.dto.response.ApiResponse;
import vuluu.postservice.dto.response.JobSkillExtractResponseDTO;
import vuluu.postservice.dto.response.UserSkillExtractResponseDTO;

public interface JobExtractServiceClient {

  @PostExchange(url = "/extract_description", contentType = MediaType.APPLICATION_JSON_VALUE)
  Mono<ApiResponse<JobSkillExtractResponseDTO>> extractJobDescription(
      @RequestBody JobSkillExtractRequestDTO requestDTO);

  @PostExchange(url = "/extract_user_skill", contentType = MediaType.APPLICATION_JSON_VALUE)
  Mono<ApiResponse<UserSkillExtractResponseDTO>> extractUserSkill(
      @RequestBody UserSkillExtractRequestDTO requestDTO);
}
