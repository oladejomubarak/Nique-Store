package oladejo.mubarak.niquestore.repository;

import oladejo.mubarak.niquestore.data.model.ConfirmationToken;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationToken, String>{
    Optional<ConfirmationToken> findByToken(String token);
    //@Transactional
    void deleteConfirmationTokensByExpiredAtBefore(LocalDateTime currentTime);




    //@Modifying
//    @Query("UPDATE ConfirmationToken confirmationToken" +
//            " SET confirmationToken.confirmedAt = ?1" +
//            " WHERE confirmationToken.token = ?2")
   // @Transactional
    void confirmedAt(LocalDateTime now, String token);
}
