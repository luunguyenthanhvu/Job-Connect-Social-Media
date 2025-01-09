package vuluu.aggregationservice.enums;

public enum ETypeNotify {
  INFO("INFO"), WARNING("WARNING"), ERROR("ERROR"),
  SUGGEST_JOB("SUGGEST_JOB");
  private final String text;

  private ETypeNotify(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
