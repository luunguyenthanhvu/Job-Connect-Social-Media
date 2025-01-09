package vuluu.notificationservice.enums;

public enum EMessage {
  SUGGEST_JOB("{0} that matches your CV"),
  APPLY_JOB("Applicant {0} has apply to {1}");
  private final String template;

  private EMessage(String template) {
    this.template = template;
  }

  public String format(Object... args) {
    return java.text.MessageFormat.format(template, args);
  }
}
