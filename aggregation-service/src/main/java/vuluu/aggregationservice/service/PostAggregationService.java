package vuluu.aggregationservice.service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.configuration.WebClientBuilder;
import vuluu.aggregationservice.dto.pageCustom.PageCustomResponseDTO;
import vuluu.aggregationservice.dto.request.EmployerInfoWithAddressRequestDTO;
import vuluu.aggregationservice.dto.request.ListUserGetImgRequestDTO;
import vuluu.aggregationservice.dto.response.ApiResponse;
import vuluu.aggregationservice.dto.response.EnrichedJobPostResponseDTO;
import vuluu.aggregationservice.dto.response.JobPostDetailsResponseCustomDTO;
import vuluu.aggregationservice.dto.response.JobPostDetailsResponseDTO;
import vuluu.aggregationservice.dto.response.JobPostEmployerInfoAddressResponseDTO;
import vuluu.aggregationservice.dto.response.JobPostListResponseDTO;
import vuluu.aggregationservice.dto.response.ListUserWithImgResponseDTO;
import vuluu.aggregationservice.repository.FileClient;
import vuluu.aggregationservice.repository.PostClient;
import vuluu.aggregationservice.repository.UserClient;

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

  public Mono<ApiResponse<PageCustomResponseDTO<EnrichedJobPostResponseDTO>>> getPageJobPost(
      int page, int size) {
    // Gọi service lấy danh sách công việc
    Mono<ApiResponse<PageCustomResponseDTO<JobPostListResponseDTO>>> jobPostResponseMono =
        WebClientBuilder.createClient(pWebClient, PostClient.class)
            .getPageJobPost(page, size);
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    return jobPostResponseMono.flatMap(jobPostResponse -> {
      List<JobPostListResponseDTO> jobs = jobPostResponse.getResult().getContent();

      // Chuẩn bị dữ liệu request cho từng service
      List<ListUserGetImgRequestDTO> userImgRequests = jobs.stream()
          .map(job -> new ListUserGetImgRequestDTO(job.getId().toString(), job.getUserId()))
          .collect(Collectors.toList());

      List<EmployerInfoWithAddressRequestDTO> employerRequests = jobs.stream()
          .map(job -> new EmployerInfoWithAddressRequestDTO(job.getId().toString(), job.getUserId(),
              Long.valueOf(job.getAddressId())))
          .collect(Collectors.toList());

      // Gọi API batch
      Mono<List<ListUserWithImgResponseDTO>> userImagesMono = WebClientBuilder.createClient(
              fWebClient, FileClient.class)
          .getUserImage(userImgRequests)
          .map(ApiResponse::getResult);

      Mono<List<JobPostEmployerInfoAddressResponseDTO>> employerInfoMono = WebClientBuilder.createClient(
              uWebClient, UserClient.class)
          .getEmployerWithAddress(employerRequests)
          .map(ApiResponse::getResult);

      // Kết hợp kết quả từ các service
      return Mono.zip(userImagesMono, employerInfoMono).map(tuple -> {
        List<ListUserWithImgResponseDTO> userImages = tuple.getT1();
        List<JobPostEmployerInfoAddressResponseDTO> employerInfos = tuple.getT2();

        // Map dữ liệu trả về vào danh sách enriched jobs
        Map<String, String> userImageMap = userImages.stream()
            .collect(Collectors.toMap(ListUserWithImgResponseDTO::getPostId,
                ListUserWithImgResponseDTO::getImg));

        Map<String, JobPostEmployerInfoAddressResponseDTO> employerInfoMap = employerInfos.stream()
            .collect(
                Collectors.toMap(JobPostEmployerInfoAddressResponseDTO::getPostId, info -> info));

        List<EnrichedJobPostResponseDTO> enrichedJobs = jobs.stream()
            .map(job -> {
                  String formattedDate = (job.getPostedDate() != null) ?
                      formatter.format(job.getPostedDate()) : "N/A";
                  return EnrichedJobPostResponseDTO.builder()
                      .id(job.getId())
                      .title(job.getTitle())
                      .userId(job.getUserId())
                      .addressId(job.getAddressId())
                      .postedDate(formattedDate)
                      .avatarUrl(userImageMap.getOrDefault(job.getId().toString(), ""))
                      .username(employerInfoMap.get(job.getId().toString()).getUsername())
                      .address(employerInfoMap.get(job.getId().toString()).getAddress())
                      .build();
                }
            ).collect(Collectors.toList());

        // Tạo response chứa dữ liệu enriched
        PageCustomResponseDTO<EnrichedJobPostResponseDTO> enrichedPage = PageCustomResponseDTO.<EnrichedJobPostResponseDTO>builder()
            .content(enrichedJobs)
            .pageable(jobPostResponse.getResult().getPageable())
            .totalPages(jobPostResponse.getResult().getTotalPages())
            .totalElements(jobPostResponse.getResult().getTotalElements())
            .size(jobPostResponse.getResult().getSize())
            .number(jobPostResponse.getResult().getNumber())
            .sort(jobPostResponse.getResult().getSort())
            .first(jobPostResponse.getResult().isFirst())
            .last(jobPostResponse.getResult().isLast())
            .numberOfElements(jobPostResponse.getResult().getNumberOfElements())
            .empty(jobPostResponse.getResult().isEmpty())
            .build();

        return ApiResponse.<PageCustomResponseDTO<EnrichedJobPostResponseDTO>>builder()
            .result(enrichedPage).build();
      });
    });
  }

  public Mono<ApiResponse<JobPostDetailsResponseCustomDTO>> getJobPostDetails(Long id) {
    Mono<ApiResponse<JobPostDetailsResponseDTO>> responseJob = WebClientBuilder.createClient(
        pWebClient, PostClient.class).getJobDetails(id);

    return responseJob.flatMap(data -> {
      JobPostDetailsResponseDTO jobDetails = data.getResult();

      ListUserGetImgRequestDTO requestImage = ListUserGetImgRequestDTO
          .builder()
          .userId(jobDetails.getUserId())
          .postId(jobDetails.getId().toString())
          .build();

      EmployerInfoWithAddressRequestDTO requestUserInfo = EmployerInfoWithAddressRequestDTO
          .builder()
          .postId(jobDetails.getId().toString())
          .userId(jobDetails.getUserId())
          .addressId(Long.valueOf(jobDetails.getAddressId()))
          .build();

      // Gọi API batch
      Mono<List<ListUserWithImgResponseDTO>> userImagesMono = WebClientBuilder.createClient(
              fWebClient, FileClient.class)
          .getUserImage(List.of(requestImage))
          .map(ApiResponse::getResult);

      Mono<List<JobPostEmployerInfoAddressResponseDTO>> employerInfoMono = WebClientBuilder.createClient(
              uWebClient, UserClient.class)
          .getEmployerWithAddress(List.of(requestUserInfo))
          .map(ApiResponse::getResult);
      // Kết hợp cả hai response lại thành một ApiResponse
      return Mono.zip(userImagesMono, employerInfoMono)
          .map(tuple -> {
            ListUserWithImgResponseDTO imageResponse = tuple.getT1().get(0);
            JobPostEmployerInfoAddressResponseDTO userInfoResponse = tuple.getT2().get(0);
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            return ApiResponse.<JobPostDetailsResponseCustomDTO>builder()
                .result(JobPostDetailsResponseCustomDTO.builder()
                    .id(jobDetails.getId())
                    .title(jobDetails.getTitle())
                    .jobDescription(jobDetails.getJobDescription())
                    .jobExpertise(jobDetails.getJobExpertise())
                    .jobWelfare(jobDetails.getJobWelfare())
                    .userId(jobDetails.getUserId())
                    .addressId(jobDetails.getAddressId())
                    .employmentType(jobDetails.getEmploymentType())
                    .numberOfPositions(jobDetails.getNumberOfPositions())
                    .postedDate(formatter.format(jobDetails.getPostedDate()))
                    .expirationDate(formatter.format(jobDetails.getExpirationDate()))
                    .isApplied(jobDetails.isApplied())
                    .address(userInfoResponse.getAddress())
                    .username(userInfoResponse.getUsername())
                    .img(imageResponse.getImg())
                    .build())
                .build();
          });
    });
  }

}
