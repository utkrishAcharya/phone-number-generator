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

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private SimUserService simUserService;

    @Autowired
    private JwtUtil jwtUtil;

    //  Signup endpoint
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest req) {
        // basic validation
        if (req.getFirstName() == null ||
                req.getLastName() == null ||
                req.getCitizenshipNumber() == null ||
                req.getPassword() == null) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Missing required signup fields");
        }

        // call service to create user
        String saved = simUserService.signup(req);
        return ResponseEntity.ok(saved);
    }

    //  Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {

        if (req.getCitizenshipNumber() == null || req.getPassword() == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Missing login fields");
        }

        Optional<SimUser> userOpt =
                simUserService.login(req.getCitizenshipNumber(), req.getPassword());

        if (userOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }

        SimUser user = userOpt.get();

        String token = jwtUtil.generateToken(user.getCitizenshipNumber());

        return ResponseEntity.ok(
                Map.of(
                        "token", token,
                        "simNumber", user.getSimNumber()
                )
        );
    }
}
