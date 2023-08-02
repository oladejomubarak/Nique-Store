package oladejo.mubarak.niquestore.config.email;

import jakarta.mail.MessagingException;

public interface EmailService {
    void send(String to, String email) throws MessagingException;
}
