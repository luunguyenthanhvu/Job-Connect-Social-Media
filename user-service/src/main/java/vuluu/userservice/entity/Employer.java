package vuluu.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
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
public class Employer implements Serializable {

  @Id
  private String id;

  @OneToOne
  @JoinColumn(name = "userId")
  @MapsId
  private User user;

  @Column(name = "description")
  String description;

  @Column(name = "website")
  String website;

  @Column(name = "country")
  String country;

  @Column(name = "industry")
  String industry;

}
