//package nepsim.controller;
//
//import nepsim.model.SimUser;
//import nepsim.repository.SimUserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Random;
//
//@RestController
//@RequestMapping("/api/simusers")
//public class SimUserController {
//
//    @Autowired
//    private SimUserRepository repository;
//
//    // Predefined signup code
//    private final String SYSTEM_CODE = "NEPSIM2026";
//
//    // Signup endpoint
//    @PostMapping("/signup")
//    public Object signup(@RequestBody SimUser simUser, @RequestParam String code) {
//        if (!SYSTEM_CODE.equals(code)) {
//            return "Invalid signup code!";
//        }
//
//        // Generate SIM number: SIM + 4-digit random number
//        String generatedSim = "SIM-" + (1000 + new Random().nextInt(9000));
//        simUser.setSimNumber(generatedSim);
//        simUser.setSignupCode(code);
//
//        repository.save(simUser);
//        return "Signup successful! Your SIM number: " + generatedSim;
//    }
//
//    // Login endpoint
//    @PostMapping("/login")
//    public Object login(@RequestParam String simNumber, @RequestParam String code) {
//        SimUser user = repository.findBySimNumber(simNumber);
//        if (user == null) return "SIM number not found!";
//        if (!user.getSignupCode().equals(code)) return "Incorrect code!";
//        return user; // Return user info
//    }
//
//    // Optional: List all users
//    @GetMapping
//    public List<SimUser> getAllUsers() {
//        return repository.findAll();
//    }
//}
package nepsim.controller;

import nepsim.model.SimUser;
import nepsim.repository.SimUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/simusers")
public class SimUserController {

    @Autowired
    private SimUserRepository repository;

    // Signup endpoint (no code required)
    @PostMapping("/signup")
    public Object signup(@RequestBody SimUser simUser) {
        // Generate SIM number: SIM + 4-digit random number
        String generatedSim = "SIM-" + (1000 + new Random().nextInt(9000));
        simUser.setSimNumber(generatedSim);

        // Generate login code: 6-digit random code
        String loginCode = String.valueOf(100000 + new Random().nextInt(900000));
        simUser.setSignupCode(loginCode);

        repository.save(simUser);

        return "Signup successful! Your SIM number: " + generatedSim + ", Login code: " + loginCode;
    }

    // Login endpoint
    @PostMapping("/login")
    public Object login(@RequestParam String simNumber, @RequestParam String loginCode) {
        SimUser user = repository.findBySimNumber(simNumber);
        if (user == null) return "SIM number not found!";
        if (!user.getSignupCode().equals(loginCode)) return "Incorrect login code!";
        return user; // Return user info
    }

    // Optional: List all users
    @GetMapping
    public List<SimUser> getAllUsers() {
        return repository.findAll();
    }
}
