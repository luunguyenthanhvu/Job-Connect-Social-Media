package vuluu.aggregationservice.repository;

import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.dto.request.EmployerInfoWithAddressRequestDTO;
import vuluu.aggregationservice.dto.request.ListUserNameRequestDTO;
import vuluu.aggregationservice.dto.response.ApiResponse;
import vuluu.aggregationservice.dto.response.ApplicantProfileResponseDTO;
import vuluu.aggregationservice.dto.response.EmployerProfileRequestDTO;
import vuluu.aggregationservice.dto.response.JobPostEmployerInfoAddressResponseDTO;
import vuluu.aggregationservice.dto.response.UserNameWithPostResponseDTO;
import vuluu.aggregationservice.dto.response.UserResponseDTO;

public interface UserClient {

  @GetExchange(url = "/user-service/users/get-info")
  Mono<ApiResponse<UserResponseDTO>> getUserInfo();

  @GetExchange(url = "/user-service/applicant/get/applicant-profile/{id}")
  Mono<ApiResponse<ApplicantProfileResponseDTO>> getApplicantProfile(@PathVariable("id") String id);

  @GetExchange(url = "/user-service/applicant/get/employer-profile/{id}")
  Mono<ApiResponse<EmployerProfileRequestDTO>> getEmployerProfile(@PathVariable("id") String id);

  @PostExchange(url = "/user-service/users/get-user-name-with-post")
  Mono<ApiResponse<List<UserNameWithPostResponseDTO>>> getUserNameWithPost(
      @RequestBody List<ListUserNameRequestDTO> requestDTO);

  @PostExchange(url = "/user-service/users/get-employer-info-with-address")
  Mono<ApiResponse<List<JobPostEmployerInfoAddressResponseDTO>>> getEmployerWithAddress(
      @RequestBody List<EmployerInfoWithAddressRequestDTO> requestDTO);


}
