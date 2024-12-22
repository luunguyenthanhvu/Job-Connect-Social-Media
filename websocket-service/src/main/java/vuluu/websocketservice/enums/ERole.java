package vuluu.notificationservice.enums;

public enum ERole {
  ADMIN("ADMIN"), EMPLOYER("EMPLOYER"), USER("Applicant");
  private final String text;

  private ERole(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
