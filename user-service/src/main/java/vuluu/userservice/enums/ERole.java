package vuluu.userservice.enums;

public enum ERole {
  ADMIN("ADMIN"), EMPLOYER("EMPLOYER"), APPLICANT("APPLICANT");
  private final String text;

  private ERole(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
