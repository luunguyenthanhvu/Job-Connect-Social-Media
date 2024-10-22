package vuluu.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
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
@Entity(name = "employer")
@PrimaryKeyJoinColumn(name = "userId")
public class Employer extends User implements Serializable {

  @Column(name = "description")
  String description;

  @Column(name = "website")
  String website;

  @Column(name = "country")
  String country;

  @Column(name = "industry")
  String industry;
}
