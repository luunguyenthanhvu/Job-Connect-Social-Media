package vuluu.fileservice.enums;

public enum EImageType {
  USER_PROFILE("USER_PROFILE"), POST_IMAGE("POST_IMAGE");
  private final String text;

  private EImageType(String text) {
    this.text = text;
  }

  @Override
  public String toString() {
    return this.text;
  }

}
