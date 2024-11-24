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
public class ResetPasswordProducer {

  KafkaTemplate<String, Object> kafkaTemplate;
  String RESET_PASS_SUBJECT = "Welcome to our app.";

  public void sendResetPass(User user, String pass) {
    SendEmailRequestDTO data = SendEmailRequestDTO
        .builder()
        .subject(RESET_PASS_SUBJECT)
        .code(pass)
        .to(SendTo
            .builder()
            .email(user.getEmail())
            .name(user.getUsername())
            .build())
        .build();

    kafkaTemplate.send(String.valueOf(KafkaTopics.RESET_PASSWORD), data);
  }
}
