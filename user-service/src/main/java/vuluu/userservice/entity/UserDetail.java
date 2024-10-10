package vuluu.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vuluu.userservice.enums.EGender;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "User_Detail")
public class UserDetail {

  @Id
  @Column(name = "userId")
  String userId;

  @OneToOne
  @MapsId
  @JoinColumn(name = "userId")
  User user;

  @Column(name = "firstname")
  String firstname;

  @Column(name = "lastname")
  String lastname;

  @Column(name = "dob")
  Date dob;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  EGender eGender;
}
