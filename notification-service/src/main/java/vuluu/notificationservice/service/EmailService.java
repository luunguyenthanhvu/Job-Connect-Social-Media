package vuluu.notificationservice.service;

import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import vuluu.notificationservice.dto.request.EmailRequestDTO;
import vuluu.notificationservice.dto.request.SendEmailRequestDTO;
import vuluu.notificationservice.dto.request.SendTo;
import vuluu.notificationservice.dto.response.EmailResponseDTO;
import vuluu.notificationservice.exception.AppException;
import vuluu.notificationservice.exception.ErrorCode;
import vuluu.notificationservice.repository.httpclient.EmailClients;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmailService {

  EmailClients emailClient;
  String apiKey = "";

  public EmailResponseDTO sendEmail(SendEmailRequestDTO request) {
    EmailRequestDTO emailRequest =
        EmailRequestDTO.builder()
            .sender(
                SendTo.builder().name("Vuluu").email("luunguuenthanhvu123@gmail.com")
                    .build())
            .to(List.of(request.getTo()))
            .subject(request.getSubject())
            .htmlContent(request.getHtmlContent())
            .build();
    try {
      return emailClient.sendEmail(apiKey, emailRequest);
    } catch (Exception e) {
      throw new AppException(ErrorCode.valueOf("loi gui mail"));
    }
  }
}
