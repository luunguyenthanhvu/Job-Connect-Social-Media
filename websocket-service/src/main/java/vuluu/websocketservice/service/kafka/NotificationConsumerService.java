package vuluu.websocketservice.service.kafka;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumerService {

  final SimpMessagingTemplate messagingTemplate;
  ObjectMapper objectMapper;

  @KafkaListener(topics = "${spring.kafka.topics.suggest-job-user}", groupId = "${spring.kafka.consumer.group-id}")
  public void listenMatchingJob(String jsonMessage) {
    // Khi nhận thông báo từ Kafka, gửi nó qua WebSocket
    messagingTemplate.convertAndSend("/topic/notifications", "test");
  }

  @KafkaListener(topics = "${spring.kafka.topics.user-matching}", groupId = "${spring.kafka.consumer.group-id}")
  public void listenMatchingUser(String jsonMessage) {

  }
}
