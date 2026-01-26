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
        this.passwordEncoder = new BCryptPasswordEncoder(); // bcrypt hashing
    }

    // Signup — store bcrypt hashed password
    public SimUser signup(SignupRequest signupRequest) {
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

        // **hash the password** before storing
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        return userRepository.save(user);
    }

    // Login — find user by citizenship number (unique) and match hashed password
    public Optional<SimUser> login(LoginRequest loginRequest) {

        // find user by unique field
        Optional<SimUser> userOpt =
                userRepository.findByCitizenshipNumber(loginRequest.getCitizenshipNumber());

        if (userOpt.isPresent()) {
            SimUser user = userOpt.get();

            // check hashed password
            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty(); // login failed
    }
}
