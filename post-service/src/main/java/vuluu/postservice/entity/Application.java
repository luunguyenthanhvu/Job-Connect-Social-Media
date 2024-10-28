package vuluu.postservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
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
@Entity(name = "application")
public class Application implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "applicationId")
  Long id;

  @Column(name = "applicantId")
  String applicantId; // ID của ứng viên

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "jobId", nullable = false)
  JobPost job;

  @Column(name = "appliedDate")
  @Default
  Date appliedDate = new Date();

  @Column(columnDefinition = "TEXT", name = "coverLetter")
  String coverLetter;
}
