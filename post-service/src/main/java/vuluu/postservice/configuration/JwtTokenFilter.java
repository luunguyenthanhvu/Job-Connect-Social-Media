package vuluu.postservice.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This is my class for extract userId from authorization
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
    log.info("Start check header ");
    if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
      log.info("Check done!");
      String token = request.getHeader("Authorization").substring(7);
      Jwt jwt = customJwtDecoder.decode(token);
      String userId = String.valueOf(jwt.getClaims().get("userId"));

      // save userId to security context
      SecurityContextHolder.getContext().setAuthentication(
          new UsernamePasswordAuthenticationToken(userId, null, Collections.emptyList())
      );
    }
  }
}
