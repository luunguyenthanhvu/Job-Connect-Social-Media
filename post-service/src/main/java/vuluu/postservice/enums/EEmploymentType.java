package vuluu.postservice.enums;

public enum EEmploymentType {
  FULL_TIME("Full-time"), PART_TIME("Part-time"), INTERNSHIP("Internship");
  private final String text;

  private EEmploymentType(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return text;
  }
}
