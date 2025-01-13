package vuluu.aggregationservice.repository;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.dto.pageCustom.PageCustomResponseDTO;
import vuluu.aggregationservice.dto.response.ApiResponse;
import vuluu.aggregationservice.dto.response.JobPostDetailsResponseDTO;
import vuluu.aggregationservice.dto.response.JobPostListResponseDTO;

public interface PostClient {

  @GetExchange(url = "/post-service/job/get/list")
  Mono<ApiResponse<PageCustomResponseDTO<JobPostListResponseDTO>>> getPageJobPost(
      @RequestParam("page") int page,
      @RequestParam("size") int size);

  @GetExchange(url = "/post-service/job/get/job-detail/{jobId}")
  Mono<ApiResponse<JobPostDetailsResponseDTO>> getJobDetails(
      @PathVariable("jobId") Long jobId);
}
