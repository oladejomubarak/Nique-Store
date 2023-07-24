package oladejo.mubarak.niquestore.repository;
import oladejo.mubarak.niquestore.data.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<AppUser,String> {
    Optional<AppUser> findUserByEmailIgnoreCase(String email);
    boolean existsUserByEmailIgnoreCase(String email);
}
