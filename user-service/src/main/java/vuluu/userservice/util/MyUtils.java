package vuluu.userservice.util;

import java.security.SecureRandom;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MyUtils {

  private static final String CHARACTERS = "abcdefjghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
  private static final int CODE_LENGTH = 6;
  private static final SecureRandom RANDOM = new SecureRandom();

  /**
   * Method create a verify code
   *
   * @return verifyCode
   */
  public String generateVerificationCode() {
    StringBuilder code = new StringBuilder(CODE_LENGTH);
    for (int i = 0; i < CODE_LENGTH; i++) {
      int index = RANDOM.nextInt(CHARACTERS.length());
      code.append(CHARACTERS.charAt(index));
    }
    return code.toString();
  }

  /**
   * This method return a userID from token get from filter
   *
   * @return userID
   */
  public String getUserId() {
    return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
