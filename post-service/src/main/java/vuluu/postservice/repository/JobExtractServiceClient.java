package vuluu.postservice.repository;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import vuluu.postservice.dto.request.JobSkillExtractRequestDTO;
import vuluu.postservice.dto.response.ApiResponse;
import vuluu.postservice.dto.response.JobSkillExtractResponseDTO;
import reactor.core.publisher.Mono;

public interface JobExtractServiceClient {

  @PostExchange(url = "/extract_skills", contentType = MediaType.APPLICATION_JSON_VALUE)
  Mono<ApiResponse<JobSkillExtractResponseDTO>> extractSkill(
      @RequestBody JobSkillExtractRequestDTO requestDTO);
}
