package vuluu.notificationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@Entity(name = "user_notification") // Tên bảng hợp lệ
public class UserNotification implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  Long id;

  @Column(name = "user_id", nullable = false)
  String userId;

  @ManyToOne
  @JoinColumn(name = "notification_id", nullable = false)
  Notification notification;

  @Column(name = "type", nullable = false)
  @Enumerated(EnumType.STRING)
  ETypeNotify type;

  @Column(name = "read_at")
  LocalDateTime readAt;

  @Column(name = "is_read", nullable = false)
  boolean isRead;
}
