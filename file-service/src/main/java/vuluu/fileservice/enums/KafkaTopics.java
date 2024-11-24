package vuluu.fileservice.enums;

public enum KafkaTopics {
  ONBOARD_SUCCESSFUL("onboard-successful"),
  USER_REGISTER("user-register"),
  SUGGEST_JOB("suggest-job-user"),
  MATCHING_USER("user-matching"),
  RESET_PASSWORD("reset-password");

  private final String topicName;

  KafkaTopics(String topicName) {
    this.topicName = topicName;
  }

  public String getTopicName() {
    return topicName;
  }
}
