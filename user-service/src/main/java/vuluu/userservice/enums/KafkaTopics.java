package vuluu.userservice.enums;

public enum KafkaTopics {
  ONBOARD_SUCCESSFUL("onboard-successful"),
  USER_REGISTER("user-register"),
  MATCHING_USER("user-matching"),
  APPLICANT_APPLY_JOB("apply-to-job"),
  NOTIFY_USER("notify-user"),
  UPLOAD_IMAGE("upload-image"),
  RESET_PASSWORD("reset-password");

  private final String topicName;

  KafkaTopics(String topicName) {
    this.topicName = topicName;
  }

  public String getTopicName() {
    return topicName;
  }
}
