package vuluu.userservice.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Custom security for end points
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SecurityConfig {

  String[] PUBLIC_ENDPOINTS = {""};

  CustomJwtDecoder customJwtDecoder;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    httpSecurity.authorizeHttpRequests(
        request -> request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS)
            .permitAll()
            .anyRequest()
            .authenticated());

    httpSecurity.oauth2ResourceServer(
        oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(customJwtDecoder)
                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
            .authenticationEntryPoint(new JwtAuthenticationEntryPoint()));

    httpSecurity.csrf(AbstractHttpConfigurer::disable);

    return httpSecurity.build();
  }

  @Bean
  JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

    return jwtAuthenticationConverter;
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }
}
