package vuluu.fileservice.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vuluu.fileservice.dto.request.UserProfileUploadRequestDTO;
import vuluu.fileservice.exception.AppException;
import vuluu.fileservice.exception.ErrorCode;
import vuluu.fileservice.service.ImageService;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class UploadImageConsumerService {

  ImageService imageService;
  ObjectMapper objectMapper;

  @KafkaListener(topics = "${spring.kafka.topics.upload-image}", groupId = "${spring.kafka.consumer.group-id}")
  public void listenUploadUserProfile(String jsonMessage) {
    UserProfileUploadRequestDTO requestDTO = null;
    try {
      requestDTO = objectMapper.readValue(jsonMessage, UserProfileUploadRequestDTO.class);
    } catch (JsonProcessingException e) {
      throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
    log.info(String.valueOf(requestDTO));
    imageService.uploadImageWithBase64(requestDTO);
  }
}
