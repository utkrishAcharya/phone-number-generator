package nepsim.repository;

import nepsim.model.SimUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimUserRepository extends MongoRepository<SimUser, String> {
    SimUser findBySimNumber(String simNumber);
}
