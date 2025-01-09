package vuluu.notificationservice.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MyUtils {

  /**
   * This method return a userID from token get from filter
   *
   * @return userID
   */
  public String getUserId() {
    return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }
}
