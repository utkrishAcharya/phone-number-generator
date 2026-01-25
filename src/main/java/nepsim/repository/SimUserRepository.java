package nepsim.repository;

import nepsim.model.SimUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface SimUserRepository extends MongoRepository<SimUser, String> {
    Optional<SimUser> findByFirstName(String firstName);
}
