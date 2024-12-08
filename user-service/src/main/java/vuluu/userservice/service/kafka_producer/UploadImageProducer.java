package vuluu.userservice.service.kafka_producer;

import java.io.IOException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vuluu.userservice.dto.request.UserProfileUploadRequestDTO;
import vuluu.userservice.entity.User;
import vuluu.userservice.enums.EImageType;
import vuluu.userservice.enums.KafkaTopics;
import vuluu.userservice.util.MyUtils;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UploadImageProducer {

  KafkaTemplate<String, Object> kafkaTemplate;
  MyUtils myUtils;

  public void uploadUserProfile(User user, byte[] img) throws IOException {
    UserProfileUploadRequestDTO data = UserProfileUploadRequestDTO
        .builder()
        .file(myUtils.compress(img))
        .type(EImageType.USER_PROFILE)
        .userId(user.getId())
        .build();
    kafkaTemplate.send(String.valueOf(KafkaTopics.UPLOAD_IMAGE), data);
  }
}
