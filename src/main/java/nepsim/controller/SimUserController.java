package nepsim.controller;

import nepsim.model.SimUser;
import nepsim.repository.SimUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api/simusers")
public class SimUserController {

    @Autowired
    private SimUserRepository repository;

    @PostMapping("/signup")
    public Object signup(@RequestBody SimUser simUser) {

        // Validate required fields (optional but recommended)
        if (simUser.getFirstName() == null || simUser.getLastName() == null ||
                simUser.getCitizenshipNumber() == null) {
            return "Missing required fields";
        }

        // Generate realistic Nepal SIM starting +977 + 9XXXXXXXX
        long number = 9000000000L + Math.abs(new Random().nextLong() % 1000000000L);
        String generatedSim = "+977" + number;

        simUser.setSimNumber(generatedSim);

        // Save all details to database
        repository.save(simUser);

        return "Signup successful! Congratulations, your official number is: " + generatedSim;
    }
}
