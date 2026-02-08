package nepsim.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    
    Optional<User> findByUsername(String username);

}
