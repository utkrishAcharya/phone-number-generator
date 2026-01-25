package nepsim.controller;

import nepsim.pojo.LoginRequest;
import nepsim.pojo.SignupRequest;
import nepsim.model.SimUser;
import nepsim.security.JwtUtil;
import nepsim.service.SimUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SimUserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {

        // Create new user from request
        SimUser user = new SimUser();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setFatherName(req.getFatherName());
        user.setMotherName(req.getMotherName());
        user.setPlace(req.getPlace());
        user.setSpouse(req.getSpouse());
        user.setCitizenshipNumber(req.getCitizenshipNumber());
        user.setDateOfBirth(req.getDateOfBirth());
        user.setBirthPlace(req.getBirthPlace());
        user.setPassword(req.getPassword());

        // Save user and generate SIM number
        SimUser saved = userService.signup(user);

        // Generate JWT token
        String token = jwtUtil.generateToken(saved.getFirstName());

        // Build JSON response
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Signup successful!");
        response.put("simNumber", saved.getSimNumber());
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        return userService.login(req.getFirstName(), req.getPassword())
                .map(u -> {
                    String token = jwtUtil.generateToken(u.getFirstName());

                    Map<String, Object> response = new HashMap<>();
                    response.put("message", "Login successful!");
                    response.put("simNumber", u.getSimNumber());
                    response.put("token", token);

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.status(401).body(
                        Map.of("error", "Invalid credentials")
                ));
    }
}
