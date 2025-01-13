package vuluu.aggregationservice.enums;

public enum EGender {
  MALE("MALE"), FEMALE("FEMALE");
  private final String text;

  private EGender(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return this.text;
  }
}
