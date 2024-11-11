package vuluu.fileservice.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vuluu.fileservice.dto.request.ImageSearchRequestDTO;
import vuluu.fileservice.dto.response.ApiResponse;
import vuluu.fileservice.dto.response.ImageResponseDTO;
import vuluu.fileservice.entity.Image;
import vuluu.fileservice.enums.EImageType;
import vuluu.fileservice.service.CloudinaryService;
import vuluu.fileservice.service.ImageService;

@RestController
@RequestMapping("/image")
public class ImageController {

  @Autowired
  private CloudinaryService cloudinaryService;

  @Autowired
  private ImageService imageService;

  @PostMapping("/upload")
  public ApiResponse<ImageResponseDTO> uploadImage(
      @RequestPart("file") MultipartFile file,
      @RequestParam("userId") String userId,
      @RequestParam("postId") String postId,
      @RequestParam("type") String type) {
    {
      String imageUrl = "";
      EImageType eImageType = EImageType.USER_PROFILE;
      try {
        switch (type) {
          case "profile":
//            eImageType = EImageType.USER_PROFILE;
            break;
          case "post":
            eImageType = EImageType.POST_IMAGE;
            break;
        }

        imageUrl = cloudinaryService.uploadImage(file, userId, postId,
            EImageType.USER_PROFILE);
        // Create DTO response
        ImageResponseDTO responseDTO = ImageResponseDTO.builder()
            .imageUrl(imageUrl)
            .userId(userId)
            .postId(postId)
            .type(eImageType.name())
            .build();

        // Wrap it in ApiResponse and return
        return new ApiResponse<>(200,
            "Image uploaded successfully", responseDTO);
      } catch (IOException e) {
        return new ApiResponse<>(500,
            "Image upload failed: " + e.getMessage(), null);

      }
    }
  }

  @GetMapping("/search")
  public ApiResponse<List<ImageResponseDTO>> searchImages(
      ImageSearchRequestDTO searchRequest) {
    try {
      List<Image> images = imageService.searchImages(searchRequest);
      List<ImageResponseDTO> imageResponseList = images.stream().map(image -> {
        return ImageResponseDTO.builder()
            .id(image.getId())
            .imageUrl(image.getImageUrl())
            .type(image.getType().name())
            .build();
      }).collect(Collectors.toList());

      return ApiResponse.<List<ImageResponseDTO>>builder()
          .code(1000)
          .message("Images found")
          .result(imageResponseList)
          .build();


    } catch (Exception e) {
      return ApiResponse.<List<ImageResponseDTO>>builder()
          .code(500)
          .message("Error retrieving images: " + e.getMessage())
          .build();
    }
  }
}
