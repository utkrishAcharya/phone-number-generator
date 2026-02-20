package nepsim.repository;

import nepsim.model.SimUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
    public interface SimUserRepository extends MongoRepository<SimUser, String> {
    // Find user by SIM number (unique)
    Optional<SimUser> findBySimNumber(String simNumber);
   
        // Find user by citizenship number (unique, used for login)
    Optional<SimUser> findByCitizenshipNumber(String citizenshipNumber);

    // Optionally find by first name (not unique — use carefully)
    Optional<SimUser> findByFirstName(String firstName);
     }
