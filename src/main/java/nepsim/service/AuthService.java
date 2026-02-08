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
        
        this.passwordEncoder = new BCryptPasswordEncoder(); // BCrypt for secure passwords
    }

    // Signup — hash password and save
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

        // Hash (encrypt) password and store
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        return userRepository.save(user);
    }

    // in AuthService
    public Optional<SimUser> loginByPhone(String phone, String password) {
        Optional<SimUser> userOpt = userRepository.findBySimNumber(phone);

        if (userOpt.isPresent() &&
                passwordEncoder.matches(password, userOpt.get().getPassword())) {
            return userOpt;
        }
        return Optional.empty();
    }

}
