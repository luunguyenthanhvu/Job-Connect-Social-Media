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
  @Column(name = "id", nullable = false, unique = true)
  private String id;

  @OneToOne
  @JoinColumn(name = "userId")
  @MapsId
  private User user;

  @Column(name = "firstname", nullable = false)
  private String firstname;

  @Column(name = "lastname", nullable = false)
  private String lastname;

  @Column(name = "dob")
  private Date dob;

  @Column(name = "summary", columnDefinition = "TEXT")
  private String summary;
  @Column(name = "education_list", columnDefinition = "TEXT")
  private String educationList;
  @Column(name = "work_experiences", columnDefinition = "TEXT")
  private String workExperiences;

  @Column(name = "skills", columnDefinition = "TEXT")
  private String skills;

  @Column(name = "certifications", columnDefinition = "TEXT")
  private String certifications;

}
