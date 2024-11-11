package vuluu.fileservice.exception;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import vuluu.userservice.dto.response.ApiResponse;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  private static final String MIN_ATTRIBUTE = "min";

  /**
   * My own handler runtime exception
   *
   * @param exception
   * @return ApiResponse
   */
  @ExceptionHandler(value = Exception.class)
  ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
    log.error("Run time exception", exception);

    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
    apiResponse.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());

    return ResponseEntity.badRequest().body(apiResponse);
  }

  /**
   * My own handler app exception
   *
   * @param exception
   * @return ApiResponse
   */
  @ExceptionHandler(value = AppException.class)
  ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
    ErrorCode errorCode = exception.getErrorCode();

    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setCode(errorCode.getCode());
    apiResponse.setMessage(errorCode.getMessage());

    return ResponseEntity.status(errorCode.getStatusCode()).body(apiResponse);
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException exception) {
    ErrorCode errorCode = ErrorCode.UNAUTHORIZED;

    return ResponseEntity.status(errorCode.getStatusCode())
        .body(ApiResponse
            .builder()
            .code(errorCode.getCode())
            .message(errorCode.getMessage())
            .build());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse> handleValidationExceptions(
      MethodArgumentNotValidException ex) {

    ApiResponse apiResponse = new ApiResponse();
    Map<String, String> errorDetails = new HashMap<>();

    ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
      errorDetails.put(fieldError.getField(), fieldError.getDefaultMessage());
    });

    apiResponse.setCode(ErrorCode.INVALID_KEY.getCode());
    apiResponse.setMessage(ErrorCode.INVALID_KEY.getMessage());
    apiResponse.setResult(errorDetails);
    return ResponseEntity.badRequest().body(apiResponse);
  }
}
