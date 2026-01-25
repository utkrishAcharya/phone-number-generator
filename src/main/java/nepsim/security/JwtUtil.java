package nepsim.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // Must be at least 32 bytes (256 bits) for HS256
    private static final String SECRET = "ThisIsAReallyLongSecretKeyWith32Chars!";

    private final SecretKey key;

    public JwtUtil() {
        // convert string to bytes & ensure key is strong enough
        key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(key)  // no algorithm param needed
                .compact();
    }
}
