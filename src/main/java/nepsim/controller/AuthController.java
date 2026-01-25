package nepsim.controller;

import nepsim.model.SimUser;
import nepsim.pojo.LoginRequest;
import nepsim.pojo.SignupRequest;
import nepsim.service.SimUserService;
import nepsim.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SimUserService simUserService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(SimUserService simUserService, JwtUtil jwtUtil) {
        this.simUserService = simUserService;
        this.jwtUtil = jwtUtil;
    }

    // Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest req) {

        SimUser savedUser = simUserService.signup(req);

        String token = jwtUtil.generateToken(savedUser.getFirstName());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Signup successful!");
        response.put("token", token);
        response.put("simNumber", savedUser.getSimNumber());

        return ResponseEntity.ok(response);
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        if (req.getFirstName() == null || req.getPassword() == null) {
            return ResponseEntity
                    .badRequest()
                    .body("Missing firstName or password");
        }

        Optional<SimUser> userOpt;
        try {
            userOpt = simUserService.login(req.getFirstName(), req.getPassword());
        } catch (Exception e) {
            e.printStackTrace(); // this will show precise cause
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Login failed: " + e.getMessage());
        }

        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }

        SimUser user = userOpt.get();
        String token = jwtUtil.generateToken(user.getFirstName());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "simNumber", user.getSimNumber()
        ));
    }


}