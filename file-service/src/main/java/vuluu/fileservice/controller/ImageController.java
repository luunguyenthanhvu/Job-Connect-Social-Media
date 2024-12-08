package vuluu.fileservice.controller;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vuluu.fileservice.dto.response.ApiResponse;
import vuluu.fileservice.dto.response.ImageResponseDTO;
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
  @PreAuthorize("hasRole('USER') or hasRole('EMPLOYER') or hasRole('ADMIN')")
  public ApiResponse<ImageResponseDTO> uploadImage(
      @RequestPart("file") MultipartFile file,
      @RequestParam("postId") String postId,
      @RequestParam("type") String type) {
    {
      String imageUrl = "";
      EImageType eImageType = null;
      try {
        switch (type) {
          case "post":
            eImageType = EImageType.POST_IMAGE;
            break;
          default:
            eImageType = EImageType.USER_PROFILE;
            break;
        }

        imageUrl = cloudinaryService.uploadImage(file, postId,
            EImageType.USER_PROFILE);
        // Create DTO response
        ImageResponseDTO responseDTO = ImageResponseDTO.builder()
            .imageUrl(imageUrl)
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
  @PreAuthorize("hasRole('USER') or hasRole('EMPLOYER') or hasRole('ADMIN')")
  public ApiResponse<String> searchImages(
      @RequestParam("postId") String postId) {
    return ApiResponse.<String>builder()
        .result(imageService.searchImages(postId)).build();
  }
}
