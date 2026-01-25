package nepsim.controller;

import nepsim.model.SimUser;
import nepsim.pojo.LoginRequest;
import nepsim.pojo.SignupRequest;
import nepsim.service.SimUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/simusers")
public class SimUserController {

    private final SimUserService simUserService;

    @Autowired
    public SimUserController(SimUserService simUserService) {
        this.simUserService = simUserService;
    }

    // SIGNUP — new user
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequest signupRequest) {
        SimUser saved = simUserService.signup(signupRequest);

        return ResponseEntity.ok(saved);
    }

    // LOGIN — user login
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {

        Optional<SimUser> userOpt = simUserService
                .login(loginRequest.getFirstName(), loginRequest.getPassword());

        if (userOpt.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials");
        }

        return ResponseEntity.ok(userOpt.get());
    }

    // GET all users
    @GetMapping
    public ResponseEntity<List<SimUser>> getAllUsers() {
        List<SimUser> users = simUserService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    // GET one user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {

        Optional<SimUser> user = simUserService.findById(id);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }
    }

    // UPDATE user by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable String id,
            @RequestBody SimUser simUser) {

        SimUser updated = simUserService.updateUser(id, simUser);

        if (updated == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        return ResponseEntity.ok(updated);
    }

    // DELETE user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {

        boolean deleted = simUserService.deleteUser(id);

        if (!deleted) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("User not found");
        }

        return ResponseEntity.ok("User deleted successfully");
    }
}
