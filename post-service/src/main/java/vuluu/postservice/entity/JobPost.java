package vuluu.postservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vuluu.postservice.enums.EEmploymentType;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "job_post")
public class JobPost implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "jobId")
  Long id;

  @Column(name = "title")
  String title;

  @Column(name = "jobDescription")
  String jobDescription;

  @Column(name = "jobExpertise")
  String jobExpertise;

  @Column(name = "jobWelfare")
  String jobWelfare;

  /**
   * employer id
   */
  @Column(name = "userId")
  String userId;

  /**
   * job address id
   */
  @Column(name = "addressId")
  String addressId;

  @Column(name = "employmentType")
  @Enumerated(value = EnumType.STRING)
  EEmploymentType employmentType;

  @Column(name = "numberOfPositions")
  int numberOfPositions;

  @Column(name = "postedDate")
  @Default
  private Date postedDate = new Date();

  @Column(name = "expirationDate", nullable = false)
  private Date expirationDate;

  @OneToMany(mappedBy = "job", fetch = FetchType.LAZY)
  Set<Application> applications;
}
