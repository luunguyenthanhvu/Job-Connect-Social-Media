package vuluu.userservice.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This is my class for extracting userId and roles from authorization.
 */
@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class JwtTokenFilter extends OncePerRequestFilter {

  CustomJwtDecoder customJwtDecoder;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      log.error("has jwt code");
      String token = authorizationHeader.substring(7);

      // Decode the token
      Jwt jwt = customJwtDecoder.decode(token);
      String userId = String.valueOf(jwt.getClaims().get("userId"));

      // Extract authorities (roles/permissions) from JWT claims
      List<SimpleGrantedAuthority> authorities = Arrays.stream(
              jwt.getClaims().get("scope").toString().split(" "))
          .map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList());

      // Save userId and authorities to security context
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(userId, null, authorities);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } else {
      log.error("No jwt code");
    }

    filterChain.doFilter(request, response);
  }
}
