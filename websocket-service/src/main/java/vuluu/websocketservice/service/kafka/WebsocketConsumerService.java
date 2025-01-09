package vuluu.websocketservice.service.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import vuluu.websocketservice.dto.request.NotificationJobMatchingRequestDTO;
import vuluu.websocketservice.dto.request.NotifyJobSkillExtractRequestDTO;
import vuluu.websocketservice.enums.EWebSocketClient;
import vuluu.websocketservice.exception.AppException;
import vuluu.websocketservice.exception.ErrorCode;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class WebsocketConsumerService {

  final SimpMessagingTemplate messagingTemplate;
  ObjectMapper objectMapper;

  @KafkaListener(topics = "${spring.kafka.topics.notify-user}", groupId = "${spring.kafka.consumer.group-id}")
  public void listenMatchingJob(String jsonMessage) {
    NotificationJobMatchingRequestDTO requestDTO = null;
    try {
      requestDTO = objectMapper.readValue(jsonMessage, NotificationJobMatchingRequestDTO.class);
    } catch (JsonProcessingException e) {
      throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
    log.info(String.valueOf(requestDTO));
    for (String userId : requestDTO.getMatchingUsers()) {
      NotifyJobSkillExtractRequestDTO data =
          NotifyJobSkillExtractRequestDTO
              .builder()
              .id(requestDTO.getId())
              .jobId(requestDTO.getJobId())
              .isRead(requestDTO.isRead())
              .message(requestDTO.getMessage())
              .build();
      sendNotificationToUser(userId, data);
    }
  }

  private void sendNotificationToUser(String userId, NotifyJobSkillExtractRequestDTO requestDTO) {
    messagingTemplate.convertAndSend(EWebSocketClient.SUBSCRIBE_NOTIFICATION.format(userId),
        requestDTO);
  }

}
