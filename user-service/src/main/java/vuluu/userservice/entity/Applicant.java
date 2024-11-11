package vuluu.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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

  @Column(name = "firstname", nullable = false)
  private String firstname;

  @Column(name = "lastname", nullable = false)
  private String lastname;

  @Column(name = "dob")
  private Date dob;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "email", nullable = false, unique = true)
  private String email;

  @Column(name = "address")
  private String address;

  @Column(name = "summary", columnDefinition = "TEXT")
  private String summary;

  @Column(name = "work_experiences", columnDefinition = "TEXT")
  private String workExperiences; // Lưu kinh nghiệm làm việc dưới dạng chuỗi

  @Column(name = "education_list", columnDefinition = "TEXT")
  private String educationList; // Lưu thông tin học vấn dưới dạng chuỗi

  @Column(name = "certifications", columnDefinition = "TEXT")
  private String certifications; // Lưu chứng chỉ dưới dạng chuỗi

  @Column(name = "skills", columnDefinition = "TEXT")
  private String skills;
}
