package vuluu.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "education")
public class Education {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "institutionName", nullable = false)
  private String institutionName;

  @Column(name = "fieldOfStudy", nullable = false)
  private String fieldOfStudy;

  @Column(name = "degree", nullable = false)
  private String degree;

  @Column(name = "startDate", nullable = false)
  private Date startDate;

  @Column(name = "endDate")
  private Date endDate;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "applicantId")
  private Applicant applicant;
}
