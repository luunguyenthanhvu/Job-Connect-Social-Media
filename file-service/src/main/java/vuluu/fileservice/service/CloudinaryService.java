package vuluu.fileservice.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import java.io.IOException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vuluu.fileservice.entity.Image;
import vuluu.fileservice.enums.EImageType;
import vuluu.fileservice.repository.ImageRepository;
import vuluu.fileservice.util.MyUtils;

@Service
public class CloudinaryService {

  private final Cloudinary cloudinary;

  @Autowired
  private ImageRepository imageRepository;

  @Autowired
  private MyUtils myUtils;

  public CloudinaryService(@Value("${cloudinary.cloud_name}") String cloudName,
      @Value("${cloudinary.api_key}") String apiKey,
      @Value("${cloudinary.api_secret}") String apiSecret) {
    cloudinary = new Cloudinary(ObjectUtils.asMap(
        "cloud_name", cloudName,
        "api_key", apiKey,
        "api_secret", apiSecret));
  }

  public String uploadImage(MultipartFile file, String postId,
      EImageType type) throws IOException {
    // Upload the image to Cloudinary
    Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

    // Get the image URL from the upload result
    String imageUrl = uploadResult.get("url").toString();

    // Get User Id From jwt
    String userId = myUtils.getUserId();

    // Create an Image entity to save to the database
    Image image = Image.builder()
        .imageUrl(imageUrl)
        .userId(userId)
        .postId(postId)
        .type(type)
        .build();

    // Save the image entity to the database
    imageRepository.save(image);

    // Return the URL as response
    return imageUrl;
  }

  public String uploadBase64Image(String base64Image) {
    try {
      // Upload ảnh lên Cloudinary
      Map<String, Object> uploadResult = cloudinary.uploader()
          .upload(base64Image, ObjectUtils.asMap("resource_type", "auto"));
      // Trả về URL của hình ảnh đã upload
      return uploadResult.get("url").toString();
    } catch (IOException e) {
      throw new RuntimeException("Error uploading image to Cloudinary", e);
    }
  }
}
