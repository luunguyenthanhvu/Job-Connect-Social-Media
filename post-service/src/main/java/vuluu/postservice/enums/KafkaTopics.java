package vuluu.postservice.enums;

public enum KafkaTopics {
  ONBOARD_SUCCESSFUL("onboard-successful"),
  USER_REGISTER("user-register"),
  MATCHING_USER("user-matching"),
  NOTIFY_USER("notify-user"),
  RESET_PASSWORD("reset-password");

  private final String topicName;

  KafkaTopics(String topicName) {
    this.topicName = topicName;
  }

  public String getTopicName() {
    return topicName;
  }
}
