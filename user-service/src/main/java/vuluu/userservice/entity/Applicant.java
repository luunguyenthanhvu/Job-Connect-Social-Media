package vuluu.userservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vuluu.userservice.enums.EGender;

@Builder
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


  @Column(name = "userEmail", nullable = false)
  private String userEmail;

  @Column(name = "dob")
  private Date dob;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  private EGender gender;

  @Column(name = "position")
  private String position;

  @Column(name = "objective", columnDefinition = "TEXT")
  private String objective;

  @Default
  @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Education> educations = new HashSet<>();

  @Default
  @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  Set<WorkExperience> workExperiences = new HashSet<>();

  @Default
  @OneToMany(mappedBy = "applicant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  Set<Project> projects = new HashSet<>();

  @Column(name = "skills", columnDefinition = "TEXT")
  private String skills;
}
