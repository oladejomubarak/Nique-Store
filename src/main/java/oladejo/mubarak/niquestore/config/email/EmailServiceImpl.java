package oladejo.mubarak.niquestore.config.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
@Async
@Slf4j
public class EmailServiceImpl implements EmailService {


    private final JavaMailSender javaMailSender;

    @Override
    public void send(String to, String email) throws MessagingException {
        try{
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, "Utf-8");
            mimeMessageHelper.setSubject("Confirm your email address");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setFrom("oladejomubarakade@gmail.com", "Nique Store Enterprise");
            mimeMessageHelper.setText(email, true);
            javaMailSender.send(mailMessage);
        } catch (MessagingException e){
            log.info("Problem 1: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (MailException e){
            log.info("Problem 2: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            log.info("problem 3: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void sendPaymentEmail(String email, String message) throws MessagingException{
        try{
            MimeMessage mailMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mailMessage, "Utf-8");
            mimeMessageHelper.setSubject("Complete your payment");
            mimeMessageHelper.setTo(email);
            mimeMessageHelper.setFrom("oladejomubarakade@gmail.com", "Nique Store Enterprise");
            mimeMessageHelper.setText(message, true);
            javaMailSender.send(mailMessage);
        } catch (MessagingException e){
            log.info("Problem 1: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (MailException e){
            log.info("Problem 2: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            log.info("problem 3: " + e.getMessage());
            throw new RuntimeException(e);
        }

    }
}
