package vuluu.notificationservice.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vuluu.notificationservice.dto.response.ApiResponse;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class Test {

  @Value("${app.bravo.api-key}")
  @NonFinal
  String api;

  @GetMapping("/api")
  ApiResponse<String> createUser() {
    String apiKeyContent;
    try {
      apiKeyContent = Files.readString(Path.of(api));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return ApiResponse.<String>builder()
        .result(apiKeyContent).build();
  }
}
