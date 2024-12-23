package vuluu.notificationservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vuluu.notificationservice.enums.EMessage;
import vuluu.notificationservice.enums.ETypeNotify;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "notification")
public class Notification implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  Long id;

  @Column(name = "message")
  @Enumerated(EnumType.STRING)
  EMessage message;

  @Column(name = "postId")
  String postId;

  @Column(name = "from")
  String from; // this is for employer maybe?

  @Column(name = "type")
  @Enumerated(EnumType.STRING)
  ETypeNotify type;

  @Column(name = "createAt")
  LocalDateTime createAt;

  @Column(name = "isGlobal")
  boolean isGlobal;

}
