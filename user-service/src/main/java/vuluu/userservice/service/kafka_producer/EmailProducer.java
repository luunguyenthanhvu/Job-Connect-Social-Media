package vuluu.userservice.service.kafka_producer;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import vuluu.userservice.dto.request.SendEmailRequestDTO;
import vuluu.userservice.dto.request.SendTo;
import vuluu.userservice.entity.User;
import vuluu.userservice.enums.KafkaTopics;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmailProducer {

  KafkaTemplate<String, Object> kafkaTemplate;
  String WELCOME_SUBJECT = "Welcome to our app.";

  public void sendEmail(User user) {
    SendEmailRequestDTO data = SendEmailRequestDTO
        .builder()
        .subject(WELCOME_SUBJECT)
        .code(user.getVerifyCode())
        .to(SendTo
            .builder()
            .email(user.getEmail())
            .name(user.getUsername())
            .build())
        .build();
    // using kafka to send email to notification service
    kafkaTemplate.send(String.valueOf(KafkaTopics.USER_REGISTER), data);
  }
}
