package vuluu.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
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
@PrimaryKeyJoinColumn(name = "userId")
public class Applicant extends User implements Serializable {

  @Column(name = "firstname")
  String firstname;

  @Column(name = "lastname")
  String lastname;

  @Column(name = "dob")
  Date dob;

  @Column(name = "address")
  String address;

  @Column(name = "cv_Link")
  String cvLink;

  @Column(name = "skills")
  String skill;
}
