package vuluu.userservice.service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vuluu.userservice.dto.request.AuthenticationRequestDTO;
import vuluu.userservice.dto.response.AuthenticationResponseDTO;
import vuluu.userservice.entity.User;
import vuluu.userservice.exception.AppException;
import vuluu.userservice.exception.ErrorCode;
import vuluu.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {

  UserRepository userRepository;
  @NonFinal
  @Value("${jwt.signerKey}")
  protected String signerKey;

  public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO requestDTO) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
    var user = userRepository.findByEmail(requestDTO.getEmail()).orElseThrow(() -> new AppException(
        ErrorCode.USER_NOT_EXISTED));

    // check if mail verify
    if (!user.isVerified()) {
      throw new AppException(ErrorCode.NOT_YET_AUTHENTICATED);
    }

    boolean authenticated = passwordEncoder.matches(requestDTO.getPassword(), user.getPassword());

    // check map password
    if (!authenticated) {
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }

    var token = generateToken(user);

    return AuthenticationResponseDTO
        .builder()
        .token(token.token)
        .expiryTime(token.expiryDate)
        .build();
  }

  private TokenInfo generateToken(User user) {
    JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

    Date issueTime = new Date();
    Date expireTime = new Date(Instant.ofEpochMilli(issueTime.getTime())
        .plus(1, ChronoUnit.HOURS)
        .toEpochMilli());

    JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
        .subject(user.getEmail())
        .issuer("vuluu")
        .issueTime(expireTime)
        .jwtID(UUID.randomUUID().toString())
        .claim("scope", buildScope(user))
        .claim("userId", user.getId())
        .build();

    Payload payload = new Payload(jwtClaimsSet.toJSONObject());

    JWSObject jwsObject = new JWSObject(header, payload);

    try {
      jwsObject.sign(new MACSigner(signerKey.getBytes()));
      return new TokenInfo(jwsObject.serialize(), expireTime);
    } catch (JOSEException e) {
      log.error("Cannot create token", e);
      throw new AppException(ErrorCode.UNAUTHENTICATED);
    }
  }

  private String buildScope(User user) {
    StringJoiner stringJoiner = new StringJoiner(" ");

    if (!CollectionUtils.isEmpty(user.getRoles())) {
      user.getRoles().forEach(role -> {
        stringJoiner.add("ROLE_" + role.getRoleName());
        if (!CollectionUtils.isEmpty(role.getPermissions())) {
          role.getPermissions().forEach(permission -> stringJoiner.add(permission.getName()));
        }
      });
    }

    return stringJoiner.toString();
  }

  private record TokenInfo(String token, Date expiryDate) {

  }
}
