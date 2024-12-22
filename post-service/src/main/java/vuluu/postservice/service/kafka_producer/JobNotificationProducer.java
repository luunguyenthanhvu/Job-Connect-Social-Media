package vuluu.postservice.service.kafka_producer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vuluu.postservice.dto.response.JobSkillExtractResponseDTO;
import vuluu.postservice.enums.KafkaTopics;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class JobNotificationProducer {

  KafkaTemplate<String, Object> kafkaTemplate;

  public void notifyJobToUser(JobSkillExtractResponseDTO data) {
    kafkaTemplate.send(String.valueOf(KafkaTopics.MATCHING_USER), data);
  }
}
