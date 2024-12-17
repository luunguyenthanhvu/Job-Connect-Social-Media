package vuluu.aggregationservice.repository;

import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;
import vuluu.aggregationservice.dto.request.ListUserGetImgRequestDTO;
import vuluu.aggregationservice.dto.response.ApiResponse;
import vuluu.aggregationservice.dto.response.ListUserWithImgResponseDTO;

public interface FileClient {

  @GetExchange(url = "/file-service/image/search")
  Mono<ApiResponse<String>> getFileData(@RequestParam("postId") String postId);

  @PostExchange(url = "/file-service/image/get-user-image")
  Mono<ApiResponse<List<ListUserWithImgResponseDTO>>> getUserImage(
      @RequestBody List<ListUserGetImgRequestDTO> requestDTO);

}
