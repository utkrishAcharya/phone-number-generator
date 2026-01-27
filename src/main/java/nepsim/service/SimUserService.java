package nepsim.service;

import nepsim.model.SimUser;
import nepsim.pojo.SignupRequest;
import nepsim.repository.SimUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SimUserService {

    @Autowired
    private SimUserRepository repository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Signup
    public SimUser signup(SignupRequest req) {

        // Hash password
        String hashedPass = passwordEncoder.encode(req.getPassword());

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
        user.setPassword(hashedPass);

        // Generate SIM number
        long number = 9000000000L + Math.abs(new Random().nextLong() % 1000000000L);
        user.setSimNumber("+977" + number);

        return repository.save(user);
    }

    // Login
    public Optional<SimUser> login(String citizenshipNumber, String rawPassword) {

        Optional<SimUser> userOpt =
                repository.findByCitizenshipNumber(citizenshipNumber);

        if (userOpt.isEmpty()) {
            return Optional.empty();
        }

        SimUser user = userOpt.get();

        if (passwordEncoder.matches(rawPassword, user.getPassword())) {
            return Optional.of(user);
        }

        return Optional.empty();
    }

    public List<SimUser> findAllUsers() {
        return repository.findAll();
    }

    public Optional<SimUser> findById(String id) {
        return repository.findById(id);
    }

    public SimUser updateUser(String id, SimUser simUser) {
        Optional<SimUser> existing = repository.findById(id);
        if (existing.isPresent()) {
            SimUser user = existing.get();

            user.setFirstName(simUser.getFirstName());
            user.setLastName(simUser.getLastName());
            user.setFatherName(simUser.getFatherName());
            user.setMotherName(simUser.getMotherName());
            user.setPlace(simUser.getPlace());
            user.setSpouse(simUser.getSpouse());
            user.setCitizenshipNumber(simUser.getCitizenshipNumber());
            user.setDateOfBirth(simUser.getDateOfBirth());
            user.setBirthPlace(simUser.getBirthPlace());

            // Hash new password if changed
            if (simUser.getPassword() != null && !simUser.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(simUser.getPassword()));
            }

            return repository.save(user);
        }
        return null;
    }

    public boolean deleteUser(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public Optional<SimUser> findByCitizenshipNumber(String citizenshipNumber) {
        return Optional.empty();
    }
}
