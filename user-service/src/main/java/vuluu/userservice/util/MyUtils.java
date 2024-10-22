package vuluu.userservice.util;

import java.security.SecureRandom;
import org.springframework.stereotype.Component;

@Component
public class MyUtils {

  private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final int CODE_LENGTH = 6; // Độ dài của mã xác thực
  private static final SecureRandom RANDOM = new SecureRandom();

  // Phương thức tạo mã xác thực
  public String generateVerificationCode() {
    StringBuilder code = new StringBuilder(CODE_LENGTH);
    for (int i = 0; i < CODE_LENGTH; i++) {
      int index = RANDOM.nextInt(CHARACTERS.length());
      code.append(CHARACTERS.charAt(index));
    }
    return code.toString();
  }
}
