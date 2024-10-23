package vuluu.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "applicant")
public class Applicant implements Serializable {

  @Id
  private String id;

  @OneToOne
  @JoinColumn(name = "userId")
  @MapsId
  private User user;

  @Column(name = "firstname")
  String firstname;

  @Column(name = "lastname")
  String lastname;

  @Column(name = "dob")
  Date dob;

  @Column(name = "address")
  String address;

  @Column(name = "skills")
  String skill;
}
