package hexlet.code.utils;

import org.instancio.Instancio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class JWTUtils {

    @Autowired
    private JwtEncoder encoder;

    //Подготовка и шифрование токена
    public String generateToken(String username) {
        Instant now = Instant.now();
        JwtClaimsSet claim = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(username)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claim)).getTokenValue();
    }
}
