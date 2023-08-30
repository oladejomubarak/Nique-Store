package oladejo.mubarak.niquestore.service;

import lombok.RequiredArgsConstructor;
import oladejo.mubarak.niquestore.data.model.ConfirmationToken;
import oladejo.mubarak.niquestore.repository.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken){
        confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getConfirmationToken(String token){
        return confirmationTokenRepository.findByToken(token);
    }
    public void deleteExpiredToken(){
        confirmationTokenRepository.deleteConfirmationTokensByExpiredAtBefore(LocalDateTime.now());
    }
    public void setConfirmedAt(String token){
        confirmationTokenRepository.confirmedAt(LocalDateTime.now(), token);
    }

}
