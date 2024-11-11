package vuluu.aggregationservice.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.dto.response.ApiResponse;
import vuluu.aggregationservice.dto.response.UserResponseDTO;
import vuluu.aggregationservice.service.AggregationService;

@RestController
@RequestMapping("/query")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class AggregationController {

  AggregationService aggregationService;

  @GetMapping("/user-info")
  public Mono<ApiResponse<UserResponseDTO>> getUserById() {
    System.out.println("Vào được user- info");
    return aggregationService.getUserInfo();
  }

}
