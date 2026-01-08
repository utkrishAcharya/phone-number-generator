package nepsim.controller;



import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/protected")
    public ResponseEntity<String> protectedEndpoint(String token) {
        // Check token (for demo, assume "secret123" is valid)
        if (token == null || !token.equals("secret123")) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("WWW-Authenticate", "Basic realm=\"Access to protected resource\"");
            return new ResponseEntity<>("Authentication required", headers, HttpStatus.UNAUTHORIZED);
        }

        return ResponseEntity.ok("Welcome! You are authenticated.");
    }
}
