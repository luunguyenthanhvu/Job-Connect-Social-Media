package vuluu.notificationservice.repository.httpclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import vuluu.notificationservice.dto.request.EmailRequestDTO;
import vuluu.notificationservice.dto.response.EmailResponseDTO;


@FeignClient(name = "email-client", url = "https://api.brevo.com")
@Service
public interface EmailClients {

  @PostMapping(value = "/v3/smtp/email", produces = MediaType.APPLICATION_JSON_VALUE)
  EmailResponseDTO sendEmail(@RequestHeader("api-key") String apiKey,
      @RequestBody EmailRequestDTO body);
}
