package nepsim.service;

import nepsim.model.SimUser;
import nepsim.repository.SimUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
public class SimUserService {

    @Autowired
    private SimUserRepository repository;

    public SimUser signup(SimUser user) {
        String sim = "+977" + (9000000000L + new Random().nextLong() % 1000000000L);
        user.setSimNumber(sim);
        return repository.save(user);
    }

    public Optional<SimUser> login(String firstName, String password) {
        Optional<SimUser> user = repository.findByFirstName(firstName);
        return user.filter(u -> u.getPassword().equals(password));
    }
}
