package vuluu.identityservice.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;
import java.util.Date;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vuluu.identityservice.dto.request.IntrospectRequest;
import vuluu.identityservice.dto.response.IntrospectResponse;
import vuluu.identityservice.exception.AppException;
import vuluu.identityservice.exception.ErrorCode;

@Service
public class IdentityService {

  @NonFinal
  @Value("${jwt.signerKey}")
  protected String signerKey;

  public IntrospectResponse introspect(IntrospectRequest request)
      throws JOSEException, ParseException {
    var token = request.getToken();
    boolean isValid = true;

    try {
      verifyToken(token);
    } catch (AppException e) {
      isValid = false;
    }

    return IntrospectResponse.builder().valid(isValid).build();
  }

  private SignedJWT verifyToken(String token) throws JOSEException, ParseException {
    JWSVerifier verifier = new MACVerifier(signerKey.getBytes());

    SignedJWT signedJWT = SignedJWT.parse(token);

    signedJWT.getJWTClaimsSet().getStringClaim("");

    Date expiryTime = signedJWT.getJWTClaimsSet().getExpirationTime();

    var verified = signedJWT.verify(verifier);

    // JWT wrong format
    if (!verified) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    // JWT expired
    if (!expiryTime.after(new Date())) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    return signedJWT;
  }
}
