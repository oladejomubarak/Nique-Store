package oladejo.mubarak.niquestore.repository;
import oladejo.mubarak.niquestore.data.model.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepo extends MongoRepository<AppUser,String> {
    Optional<AppUser> findAppUserByEmailIgnoreCase(String email);
    Optional<AppUser> findAppUserByPhoneNumber(String phoneNumber);
    Optional<AppUser> findAppUserByEmailIgnoreCaseOrPhoneNumber(String email, String phoneNumber);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByEmailIgnoreCaseOrPhoneNumber(String email, String phoneNumber);
}
