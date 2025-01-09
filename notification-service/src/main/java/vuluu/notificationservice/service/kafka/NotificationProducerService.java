package vuluu.notificationservice.service.kafka;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vuluu.notificationservice.dto.request.NotificationJobMatchingRequestDTO;
import vuluu.notificationservice.enums.KafkaTopics;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NotificationProducerService {

  KafkaTemplate<String, Object> kafkaTemplate;

  public void notifyJobToUser(NotificationJobMatchingRequestDTO data) {
    kafkaTemplate.send(String.valueOf(KafkaTopics.NOTIFY_USER), data);
  }
}
