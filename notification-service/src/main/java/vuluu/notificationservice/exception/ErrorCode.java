package vuluu.notificationservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
  UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
  INVALID_KEY(1001, "Incorrect format", HttpStatus.BAD_REQUEST),
  USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
  USERNAME_INVALID(1003, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
  INVALID_PASSWORD(1004, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
  USER_NOT_EXISTED(1005, "User not existed", HttpStatus.NOT_FOUND),
  UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
  EXPIRED_CODE(1007, "Login session expired, please log in again.", HttpStatus.BAD_REQUEST),
  UNAUTHORIZED(1008, "You do not have permission", HttpStatus.FORBIDDEN),
  INVALID_DOB(1009, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
  NOT_YET_AUTHENTICATED(1010, "Please check your email for authenticate.", HttpStatus.BAD_REQUEST),
  VERIFY_TIME_OUT(1011, "Verify code out date. Please verify again.", HttpStatus.BAD_REQUEST),
  WRONG_VERIFY_CODE(1012, "Verify code wrong. Please enter again.", HttpStatus.BAD_REQUEST),
  JOB_NOT_EXISTED(1013, "Job not found!", HttpStatus.BAD_REQUEST);

  ErrorCode(int code, String message, HttpStatusCode statusCode) {
    this.code = code;
    this.message = message;
    this.statusCode = statusCode;
  }

  private final int code;
  private final String message;
  private final HttpStatusCode statusCode;
}
