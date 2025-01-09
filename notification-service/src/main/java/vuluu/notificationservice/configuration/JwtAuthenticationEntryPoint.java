package vuluu.postservice.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import vuluu.postservice.dto.response.ApiResponse;
import vuluu.postservice.exception.ErrorCode;

/**
 * Custom implementation of AuthenticationEntryPoint to handle unauthorized requests. This class
 * provides a custom API response instead of the default authentication response.
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  /**
   * Customizes the response for unauthorized requests.
   *
   * @param request       the HttpServletRequest
   * @param response      the HttpServletResponse
   * @param authException the authentication exception that triggered this method
   * @throws IOException      if an input or output exception occurs
   * @throws ServletException if the request could not be handled
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {
    ErrorCode errorCode = ErrorCode.UNAUTHENTICATED;

    response.setStatus(errorCode.getStatusCode().value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);

    ApiResponse<?> apiResponse = ApiResponse
        .builder()
        .code(errorCode.getCode())
        .message(errorCode.getMessage())
        .build();

    ObjectMapper objectMapper = new ObjectMapper();

    response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    // Flush the response to ensure the output is sent immediately
    response.flushBuffer();
  }
}
