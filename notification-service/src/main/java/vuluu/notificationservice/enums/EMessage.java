package vuluu.notificationservice.enums;

public enum EMessage {
  SUGGEST_JOB("{0} has posted a job that matches your CV");

  private final String template;

  private EMessage(String template) {
    this.template = template;
  }

  public String format(Object... args) {
    return java.text.MessageFormat.format(template, args);
  }
}
