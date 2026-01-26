package nepsim.service;

import nepsim.model.SimUser;
import nepsim.pojo.SignupRequest;
import nepsim.repository.SimUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class SimUserService {

    @Autowired
    private SimUserRepository repository;

    // Signup — now accepts SignupRequest
    public String signup(SignupRequest req) {
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
        user.setPassword(req.getPassword());

        // Generate Nepal SIM
        long number = 9000000000L + Math.abs(new Random().nextLong() % 1000000000L);
        user.setSimNumber("+977" + number);

        repository.save(user);
        return "Your Number is : "+ user.getSimNumber();
    }

    // Login
    public Optional<SimUser> login(String firstName, String password) {
        Optional<SimUser> user = repository.findByFirstName(firstName);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
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
            user.setPassword(simUser.getPassword());
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
}
