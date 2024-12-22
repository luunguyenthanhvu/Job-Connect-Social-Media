package vuluu.fileservice.service;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vuluu.fileservice.dto.request.ListUserGetImgRequestDTO;
import vuluu.fileservice.dto.request.UserProfileUploadRequestDTO;
import vuluu.fileservice.dto.response.ListUserWithImgResponseDTO;
import vuluu.fileservice.entity.Image;
import vuluu.fileservice.exception.AppException;
import vuluu.fileservice.exception.ErrorCode;
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
//  @Cacheable(value = "fileInfoCache", key = "'file:' + #userId + #postId", unless = "#result == null")
  public String searchImages(String postId) {
    String userId = myUtils.getUserId();
    log.error("tìm ảnh");

    // Kiểm tra nếu postId không null và không rỗng
    if (postId != null && !postId.isEmpty()) {
      log.error("tìm với bài post");
      // Xử lý tìm ảnh theo postId
      return ""; // Giả sử bạn chưa xử lý phần này
    } else {
      log.error("tìm với userID");
      log.error(imageRepository.findByUserId(userId) + " ");
      Image imageList = imageRepository.findFirstByUserIdOrderByIdDesc(userId);

      // Kiểm tra danh sách ảnh có phần tử hay không
      if (imageList != null) {
        // Trả về URL ảnh đầu tiên từ danh sách
        return imageList.getImageUrl();
      }
      return ""; // Trả về chuỗi rỗng nếu không có ảnh
    }
  }

  public List<ListUserWithImgResponseDTO> searchUserImages(
      List<ListUserGetImgRequestDTO> requestDTO) {
    log.error("tìm với userID");
    var response = new ArrayList<ListUserWithImgResponseDTO>();
    requestDTO.forEach(request -> {
      var img = getUserImg(request.getUserId());
      response.add(ListUserWithImgResponseDTO
          .builder()
          .img(img.getImg())
          .postId(request.getPostId())
          .build());
    });
    return response;
  }

  //  @Cacheable(value = "fileInfoCache", key = "'file:' + #userId", unless = "#result == null")
  public ListUserWithImgResponseDTO getUserImg(String userId) {
    Image imageList = imageRepository.findFirstByUserIdOrderByIdDesc(userId);

    // Kiểm tra danh sách ảnh có phần tử hay không
    if (imageList != null) {
      // Trả về URL ảnh đầu tiên từ danh sách
      return ListUserWithImgResponseDTO
          .builder()
          .img(imageList.getImageUrl())
          .build();
    }
    return ListUserWithImgResponseDTO
        .builder()
        .img("")
        .build();
  }

  @Transactional
  //@Cacheable(value = "fileInfoCache", key = "'file:' + #userId", unless = "#result == null")
  public void uploadImageWithByte(UserProfileUploadRequestDTO requestDTO) {
    String userId = requestDTO.getUserId();
    try {
      String imgUrl = cloudinaryService.uploadImage(requestDTO.getFile());
      var image = Image
          .builder()
          .imageUrl(imgUrl)
          .userId(requestDTO.getUserId())
          .type(requestDTO.getType())
          .build();
      imageRepository.save(image);
    } catch (Exception e) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
  }
}
