package nepsim.repository;

import nepsim.model.SimUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SimUserRepository extends MongoRepository<SimUser, String> {

    Optional<SimUser> findBySimNumber(String simNumber);

    Optional<SimUser> findByFirstName(String firstName);  // ← add this!
}
