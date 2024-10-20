package vuluu.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "User")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "userId", updatable = false, nullable = false)
  String id;

  @Column(name = "username")
  String username;

  @Column(name = "password")
  String password;

  @Column(name = "email", unique = true)
  String email;

  @Column(name = "verified")
  @Builder.Default
  boolean verified = false;

  @Column(name = "verifyCode")
  String verifyCode;

  @Column(name = "description")
  String description;

  @Column(name = "createdDate")
  @Default
  LocalDateTime createdDate = LocalDateTime.now();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
  Set<Address> addresses;

  @ManyToMany
  Set<Role> roles;

}
