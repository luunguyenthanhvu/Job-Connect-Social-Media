package vuluu.fileservice.enums;

public enum ERole {
  ADMIN("ADMIN"), EMPLOYER("EMPLOYER"), USER("USER");
  private final String text;

  private ERole(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
