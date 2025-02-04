package vuluu.userservice.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDateTime;
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

@Builder
@Getter
@Setter
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

  @Column(name = "phoneNumber")
  String phoneNumber;

  @Column(name = "verified")
  @Builder.Default
  boolean verified = false;

  @Column(name = "verifyCode")
  String verifyCode;

  @Column(name = "description", columnDefinition = "TEXT")
  String description;

  @Column(name = "createdDate")
  @Default
  LocalDateTime createdDate = LocalDateTime.now();

  @Column(name = "website")
  String website;

  @Column(name = "verificationSentDate")
  @Default
  private LocalDateTime verificationSentDate = LocalDateTime.now();

  @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @Default
  Set<Address> addresses = new HashSet<>();

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  @Default
  private Set<Role> roles = new HashSet<>();
}
