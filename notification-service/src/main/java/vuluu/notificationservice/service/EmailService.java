package vuluu.notificationservice.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class EmailService {

  EmailClients emailClient;

  @Value("${app.bravo.api-key}")
  @NonFinal
  String apiKeyPath;

  @Value("${app.name}")
  @NonFinal
  String appName;

  @Value("${app.email}")
  @NonFinal
  String hostEmail;

  public EmailResponseDTO sendEmailVerify(SendEmailRequestDTO request) {
    String htmlContent;
    String apiKey;
    try {
      htmlContent = new String(Files.readAllBytes(Paths.get(
          "C:\\Users\\PC\\Desktop\\Graduate Thesis-BackEnd\\Job-Connect-Social-Media\\notification-service\\src\\main\\resources\\templates\\verify.html")));
      apiKey = Files.readString(Path.of(apiKeyPath));
    } catch (IOException e) {
      log.error(e.toString());
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    // replace into verify code for user
    htmlContent = htmlContent.replace("{verificationCode}", request.getCode());

    EmailRequestDTO emailRequest =
        EmailRequestDTO.builder()
            .sender(
                SendTo
                    .builder()
                    .name(appName)
                    .email(hostEmail)
                    .build())
            .to(List.of(request.getTo()))
            .subject(request.getSubject())
            .htmlContent(htmlContent)
            .build();

    // send email
    return sendEmail(emailRequest, apiKey);
  }

  public EmailResponseDTO sendEmailResetPass(SendEmailRequestDTO request) {
    String htmlContent;
    String apiKey;
    try {
      htmlContent = new String(Files.readAllBytes(Paths.get(
          "C:\\Users\\PC\\Desktop\\Graduate Thesis-BackEnd\\Job-Connect-Social-Media\\notification-service\\src\\main\\resources\\templates\\resetPass.html")));
      apiKey = Files.readString(Path.of(apiKeyPath));
    } catch (IOException e) {
      log.error(e.toString());
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    // replace into verify code for user
    htmlContent = htmlContent.replace("{password}", request.getCode());

    EmailRequestDTO emailRequest =
        EmailRequestDTO.builder()
            .sender(
                SendTo
                    .builder()
                    .name(appName)
                    .email(hostEmail)
                    .build())
            .to(List.of(request.getTo()))
            .subject(request.getSubject())
            .htmlContent(htmlContent)
            .build();

    // send email
    return sendEmail(emailRequest, apiKey);
  }

  private EmailResponseDTO sendEmail(EmailRequestDTO emailRequestDTO, String apiKey) {
    try {
      return emailClient.sendEmail(apiKey, emailRequestDTO);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
  }
}
