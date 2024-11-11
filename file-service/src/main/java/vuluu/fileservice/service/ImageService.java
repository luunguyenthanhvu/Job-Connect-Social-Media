package vuluu.fileservice.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vuluu.fileservice.dto.request.ImageSearchRequestDTO;
import vuluu.fileservice.entity.Image;
import vuluu.fileservice.repository.ImageRepository;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {

  ImageRepository imageRepository;

  // Phương thức tìm kiếm hình ảnh theo userId hoặc postId
  public List<Image> searchImages(ImageSearchRequestDTO searchRequest) {
    if (searchRequest.getUserId() != null) {
      return imageRepository.findByUserId(searchRequest.getUserId());
    } else if (searchRequest.getPostId() != null) {
      return imageRepository.findByPostId(searchRequest.getPostId());
    } else {
      throw new IllegalArgumentException("Either userId or postId must be provided");
    }
  }
}
