package vuluu.userservice.configuration;

import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = false)
public class CustomJwtDecoder implements JwtDecoder {

  @Value("${jwt.signerKey}")
  String signerKey;

  @Override
  public Jwt decode(String token) throws JwtException {
    try {
      SignedJWT signedJWT = SignedJWT.parse(token);
      return new Jwt(token,
          signedJWT.getJWTClaimsSet().getIssueTime().toInstant(),
          signedJWT.getJWTClaimsSet().getExpirationTime().toInstant(),
          signedJWT.getHeader().toJSONObject(),
          signedJWT.getJWTClaimsSet().getClaims()
      );
    } catch (ParseException e) {
      throw new JwtException("Invalid token");
    }
  }
}
