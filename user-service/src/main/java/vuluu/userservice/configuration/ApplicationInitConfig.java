package vuluu.userservice.configuration;

import java.time.LocalDateTime;
import java.util.HashSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import vuluu.userservice.entity.Role;
import vuluu.userservice.entity.User;
import vuluu.userservice.enums.ERole;
import vuluu.userservice.repository.RoleRepository;
import vuluu.userservice.repository.UserRepository;

/**
 * This is my init config for create default value
 */
@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {

  PasswordEncoder passwordEncoder;
  @NonFinal
  static final String ADMIN_USER_NAME = "admin";

  @NonFinal
  static final String ADMIN_EMAIL = "admin@gmail.com";

  @NonFinal
  static final String ADMIN_PASSWORD = "admin";

  @Bean
  @ConditionalOnProperty(
      prefix = "spring",
      value = "datasource.driverClassName",
      havingValue = "com.mysql.cj.jdbc.Driver"
  )
  ApplicationRunner applicationRunner(UserRepository userRepository,
      RoleRepository roleRepository) {
    log.info("Initializing application....");
    return args -> {
      if (!userRepository.findByEmail(ADMIN_EMAIL).isPresent()) {

        Role adminRole = roleRepository.save(Role
            .builder()
            .roleId(ERole.ADMIN)
            .roleName(ERole.ADMIN)
            .description("Admin role")
            .build());

        roleRepository.save(Role
            .builder()
            .roleId(ERole.EMPLOYER)
            .roleName(ERole.EMPLOYER)
            .description("Employer role")
            .build());

        roleRepository.save(Role
            .builder()
            .roleId(ERole.USER)
            .roleName(ERole.USER)
            .description("USER role")
            .build());

        var roles = new HashSet<Role>();
        roles.add(adminRole);

        User user = User
            .builder()
            .createdDate(LocalDateTime.now())
            .email(ADMIN_EMAIL)
            .username(ADMIN_USER_NAME)
            .password(passwordEncoder.encode(ADMIN_PASSWORD))
            .roles(roles)
            .verified(true)
            .description("This is account admin default")
            .build();

        userRepository.save(user);
        log.warn("Admin has been created by default password");
      }
      log.info("Application initialization completed.....");
    };
  }
}
