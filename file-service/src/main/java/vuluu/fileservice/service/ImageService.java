package vuluu.fileservice.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import vuluu.fileservice.dto.request.UserProfileUploadRequestDTO;
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
  CloudinaryService cloudinaryService;

  // Phương thức tìm kiếm hình ảnh theo userId hoặc postId
  // Redis Cacheable check cho file dữ liệu
  @Cacheable(value = "fileInfoCache", key = "'file:' + #userId", unless = "#result == null")
  public List<Image> searchImages(String postId) {
    String userId = myUtils.getUserId();
    if (postId != null || !postId.isEmpty()) {
      return imageRepository.findByUserIdAndPostId(userId, postId);
    } else {
      return imageRepository.findByUserId(userId);
    }
  }

  public void uploadImageWithBase64(UserProfileUploadRequestDTO requestDTO) {
    String imgUrl = cloudinaryService.uploadBase64Image(requestDTO.getFile());
    var image = Image
        .builder()
        .imageUrl(imgUrl)
        .userId(requestDTO.getUserId())
        .type(requestDTO.getType())
        .build();
    imageRepository.save(image);
  }
}
