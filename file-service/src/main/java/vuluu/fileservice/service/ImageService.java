package vuluu.fileservice.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vuluu.fileservice.entity.Image;
import vuluu.fileservice.repository.ImageRepository;
import vuluu.fileservice.util.MyUtils;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {

  ImageRepository imageRepository;
  MyUtils myUtils;

  // Phương thức tìm kiếm hình ảnh theo userId hoặc postId
  public List<Image> searchImages(String postId) {
    String userId = myUtils.getUserId();
    if (postId != null || !postId.isEmpty()) {
      return imageRepository.findByUserIdAndPostId(userId, postId);
    } else {
      return imageRepository.findByUserId(userId);
    }
  }
}
