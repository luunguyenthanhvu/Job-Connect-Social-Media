package vuluu.userservice.enums;

public enum EGender {
  MALE("MALE"), FEMALE("FEMALE"), COMPANY("COMPANY");
  private final String text;

  private EGender(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return this.text;
  }
}
