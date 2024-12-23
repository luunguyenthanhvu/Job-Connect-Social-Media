package vuluu.websocketservice.enums;

public enum EWebSocketClient {
  SUBSCRIBE_NOTIFICATION("/user/{0}/queue/notifications");

  private final String template;

  private EWebSocketClient(String template) {
    this.template = template;
  }

  public String format(Object... args) {
    return java.text.MessageFormat.format(template, args);
  }
}
