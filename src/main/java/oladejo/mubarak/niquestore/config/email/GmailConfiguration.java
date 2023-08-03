package oladejo.mubarak.niquestore.config.email;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Getter
@Setter
@Configuration
public class GmailConfiguration {
    //@Value("${spring.mail.host}")
    private final String host = System.getenv("MAIL_HOST");

    //@Value("${spring.mail.port}")
    private final int port = Integer.parseInt(System.getenv("MAIL_HOST"));

    //@Value("${spring.mail.username}")
    private final String username = System.getenv("MAIL_USERNAME");

    //@Value("${spring.mail.password}")
    private final String password = System.getenv("MAIL_PASSWORD");

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        props.put("mail.smtp.ssl.enable", "true");
        return mailSender;
    }
}
