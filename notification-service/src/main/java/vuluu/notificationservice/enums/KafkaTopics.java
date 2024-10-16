package vuluu.notificationservice.enums;

public enum KafkaTopics {
  ONBOARD_SUCCESSFUL("onboard-successful"),
  USER_REGISTER("user-register"),
  ACCOUNT_VERIFICATION("account-verification");

  private final String topicName;

  KafkaTopics(String topicName) {
    this.topicName = topicName;
  }

  public String getTopicName() {
    return topicName;
  }
}
