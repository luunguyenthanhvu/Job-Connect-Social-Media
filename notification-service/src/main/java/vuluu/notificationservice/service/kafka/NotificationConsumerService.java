package vuluu.notificationservice.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import vuluu.notificationservice.dto.request.ApplicantApplyJobRequestDTO;
import vuluu.notificationservice.dto.request.SendEmailRequestDTO;
import vuluu.notificationservice.dto.response.JobSkillExtractResponseDTO;
import vuluu.notificationservice.exception.AppException;
import vuluu.notificationservice.exception.ErrorCode;
import vuluu.notificationservice.service.EmailService;
import vuluu.notificationservice.service.NotificationService;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class NotificationConsumerService {

  EmailService emailService;
  NotificationService notificationService;
  ObjectMapper objectMapper;

  @KafkaListener(topics = "${spring.kafka.topics.user-register}", groupId = "${spring.kafka.consumer.group-id}")
  public void listenEmailRegister(String jsonMessage) {
    SendEmailRequestDTO requestDTO = null;
    try {
      requestDTO = objectMapper.readValue(jsonMessage, SendEmailRequestDTO.class);
    } catch (JsonProcessingException e) {
      throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
    log.info(String.valueOf(requestDTO));
    emailService.sendEmailVerify(requestDTO);
  }

  @KafkaListener(topics = "${spring.kafka.topics.reset-password}", groupId = "${spring.kafka.consumer.group-id}")
  public void listenResetPassRegister(String jsonMessage) {
    SendEmailRequestDTO requestDTO = null;
    try {
      requestDTO = objectMapper.readValue(jsonMessage, SendEmailRequestDTO.class);
    } catch (JsonProcessingException e) {
      throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
    log.info(String.valueOf(requestDTO));
    emailService.sendEmailResetPass(requestDTO);
  }

  @KafkaListener(topics = "${spring.kafka.topics.user-matching}", groupId = "${spring.kafka.consumer.group-id}")
  public void listenMatchingUser(String jsonMessage) {
    JobSkillExtractResponseDTO requestDTO = null;
    try {
      requestDTO = objectMapper.readValue(jsonMessage, JobSkillExtractResponseDTO.class);
    } catch (JsonProcessingException e) {
      throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
    log.info(String.valueOf(requestDTO));
    notificationService.sendSuggestJobToUsers(requestDTO);
  }

  @KafkaListener(topics = "${spring.kafka.topics.apply-to-job}", groupId = "${spring.kafka.consumer.group-id}")
  public void listenMApplyUser(String jsonMessage) {
    ApplicantApplyJobRequestDTO requestDTO = null;
    try {
      requestDTO = objectMapper.readValue(jsonMessage, ApplicantApplyJobRequestDTO.class);
    } catch (JsonProcessingException e) {
      throw new AppException(ErrorCode.UNCATEGORIZED_EXCEPTION);
    }
    log.info(String.valueOf(requestDTO));
    notificationService.sendNotifyNewApplicants(requestDTO);
  }
}
