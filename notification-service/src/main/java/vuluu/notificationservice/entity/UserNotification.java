package vuluu.notificationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vuluu.notificationservice.enums.ETypeNotify;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "user_notification")
public class UserNotification implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  Long id;

  @Column(name = "userId", nullable = false)
  String userId;

  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "notificationId", referencedColumnName = "id")
  Notification notification;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  ETypeNotify type;


  @Column(name = "isRead", nullable = false)
  boolean isRead;
}
