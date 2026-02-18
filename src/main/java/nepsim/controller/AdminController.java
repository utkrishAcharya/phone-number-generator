package nepsim.controller;

import nepsim.model.SimUser;
import nepsim.service.SimUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
    
@RequestMapping("/admin")
    public class AdminController {

    private final SimUserService simUserService;

    @Autowired
    public AdminController(SimUserService simUserService) {
        this.simUserService = simUserService;
    }

    // Get all users (admin only)
     @GetMapping("/users")
     public ResponseEntity<List<SimUser>> getAllUsers() {
        List<SimUser> users = simUserService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    // Update specific user
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id,
                                        
        @RequestBody SimUser simUser) {
         SimUser updated = simUserService.updateUser(id, simUser);

        if (updated == null) {
            
            // Return 404 if not found
             return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "User not found"));
        }

        return ResponseEntity.ok(updated);
    }

    // Delete user
    @DeleteMapping("/users/{id}")
    
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

