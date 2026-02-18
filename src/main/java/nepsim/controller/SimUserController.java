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
import java.util.Map;
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
        
        // (your signup logic here — already working)
        Optional<SimUser> existingUser = simUserService.findByCitizenshipNumber(signupRequest.getCitizenshipNumber());

                
        if (existingUser.isPresent()) {
            
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of("message", "User already exists"));
        }

        SimUser newUser = simUserService.signup(signupRequest);
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("simNumber", newUser.getSimNumber()));
    }

    // LOGIN — user login (by phone number + password)
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        
   Optional<SimUser> userOpt = simUserService.loginByPhone(loginRequest.getPhoneNumber(), loginRequest.getPassword());
            
        if (userOpt.isEmpty()) {
            
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid phone number or password");
        }

        SimUser user = userOpt.get();
        user.setPassword(null); // hide the password
        return ResponseEntity.ok(user);
    }

    // GET all users
    @GetMapping
    public ResponseEntity<List<SimUser>> getAllUsers() {
        List<SimUser> users = simUserService.findAllUsers();
        users.forEach(u -> u.setPassword(null));
        return ResponseEntity.ok(users);
    }

    // GET one user by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        Optional<SimUser> user = simUserService.findById(id);
        if (user.isPresent()) {
            SimUser u = user.get();
            u.setPassword(null);
            return ResponseEntity.ok(u);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("message", "User not found"));
    }

    // UPDATE user by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable String id,
            @Valid @RequestBody SimUser simUser) {

        SimUser updated = simUserService.updateUser(id, simUser);
        if (updated == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }
        updated.setPassword(null);
        return ResponseEntity.ok(updated);
    }

    // DELETE user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        boolean deleted = simUserService.deleteUser(id);
        if (!deleted) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
}
