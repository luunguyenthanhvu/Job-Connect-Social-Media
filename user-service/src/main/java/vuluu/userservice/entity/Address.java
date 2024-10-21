package vuluu.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity(name = "address")
public class Address implements Serializable {

  @Id
  @Column(name = "addressId")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  long id;

  @Column(name = "addressDescription")
  String addressDescription;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "userId")
  User user;
}
