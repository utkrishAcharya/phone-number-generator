package nepsim.service;

import nepsim.model.SimUser;
import nepsim.pojo.LoginRequest;
import nepsim.pojo.SignupRequest;
import nepsim.repository.SimUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final SimUserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(SimUserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();  // secure hashing
    }

    public SimUser signup(SignupRequest signupRequest) {

        // Create a new user entity
        SimUser user = new SimUser();

        user.setFirstName(signupRequest.getFirstName());
        user.setLastName(signupRequest.getLastName());
        user.setFatherName(signupRequest.getFatherName());
        user.setMotherName(signupRequest.getMotherName());
        user.setPlace(signupRequest.getPlace());
        user.setSpouse(signupRequest.getSpouse());
        user.setCitizenshipNumber(signupRequest.getCitizenshipNumber());
        user.setDateOfBirth(signupRequest.getDateOfBirth());
        user.setBirthPlace(signupRequest.getBirthPlace());

        // Hash the password for security
        String hashed = passwordEncoder.encode(signupRequest.getPassword());
        user.setPassword(hashed);

        return userRepository.save(user);
    }

    public Optional<SimUser> login(LoginRequest loginRequest) {

        // Find user by firstName (or you can change to username/email)
        Optional<SimUser> userOpt = userRepository.findByFirstName(loginRequest.getFirstName());

        if (userOpt.isPresent()) {
            SimUser user = userOpt.get();

            // Check hashed password
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}
