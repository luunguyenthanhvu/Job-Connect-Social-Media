package vuluu.userservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import java.io.Serializable;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vuluu.userservice.enums.ERole;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "Role")
public class Role implements Serializable {

  @Id
  @Column(name = "roleId")
  @Enumerated(EnumType.STRING)
  ERole roleId;

  @Column(name = "roleName")
  @Enumerated(EnumType.STRING)
  ERole roleName;

  @Column(name = "description")
  String description;

  @ManyToMany
  Set<Permission> permissions;
}
