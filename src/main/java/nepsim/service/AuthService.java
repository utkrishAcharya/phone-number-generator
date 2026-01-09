package nepsim.service;

import nepsim.pojo.LoginRequest;
import nepsim.pojo.SignupRequest;
import nepsim.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

//    public User signup(SignupRequest signupRequest) {
//        User user = new User();
//        user.getUsername();
//        user.getPassword();
//        return userRepository.save(user);
//
//        }

   public User login(LoginRequest loginRequest) {
       return userRepository.findByUsername(loginRequest.getUsername())
               .filter(u -> u.getPassword().equals(loginRequest.getPassword()))
               .orElse(null);
    }
   }