package vuluu.aggregationservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.dto.pageCustom.PageCustomResponseDTO;
import vuluu.aggregationservice.dto.response.ApiResponse;
import vuluu.aggregationservice.dto.response.JobPostListResponseDTO;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostAggregationService {

  private WebClient pWebClient;
  private WebClient uWebClient;
  private WebClient fWebClient;

  @Autowired
  private void setPostWebClient(@Qualifier("postWebClient") WebClient webClient) {
    this.pWebClient = webClient;
  }

  @Autowired
  private void setUserWebClient(@Qualifier("userWebClient") WebClient webClient) {
    this.uWebClient = webClient;
  }

  @Autowired
  private void setFileWebClient(@Qualifier("fileWebClient") WebClient webClient) {
    this.fWebClient = webClient;
  }

  public Mono<ApiResponse<PageCustomResponseDTO<JobPostListResponseDTO>>> getPageJobPost(int page,
      int size) {
    return null;
  }
}
