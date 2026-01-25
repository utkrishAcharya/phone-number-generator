package nepsim.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtil {

    // Must be at least 256 bits for HS256
    private static final String SECRET = "ThisIsAReallyLongSecretKeyWithAtLeast32Bytes!1234";
    private static final long EXPIRATION_TIME = 1000L * 60 * 60 * 24; // 24 hours

    private final SecretKey key;

    public JwtUtil() {
        // Create a SecretKey from the long secret string
        key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Generate a JWT token for the given user (subject)
     */
    public String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)  // HS256 by default
                .compact();
    }

    /**
     * Extract subject (username/first name) from the token
     */
    public String extractUsername(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload(); // get the claims (body)

            return claims.getSubject();
        } catch (Exception e) {
            return null; // invalid token
        }
    }

    /**
     * Validate token signature and expiration
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            Date expiration = claims.getExpiration();
            return expiration != null && expiration.after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
