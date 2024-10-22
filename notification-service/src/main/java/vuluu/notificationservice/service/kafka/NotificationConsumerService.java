package vuluu.notificationservice.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vuluu.notificationservice.dto.request.SendEmailRequestDTO;
import vuluu.notificationservice.exception.AppException;
import vuluu.notificationservice.exception.ErrorCode;
import vuluu.notificationservice.service.EmailService;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumerService {

  EmailService emailService;
  ObjectMapper objectMapper;

  @KafkaListener(topics = "${spring.kafka.topics.user-register}", groupId = "notification-group")
  public void listenEmailSender(String jsonMessage) {
    SendEmailRequestDTO requestDTO = null;
    try {
      requestDTO = objectMapper.readValue(jsonMessage, SendEmailRequestDTO.class);
    } catch (JsonProcessingException e) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
    log.info(String.valueOf(requestDTO));
    emailService.sendEmail(requestDTO);
  }
}
