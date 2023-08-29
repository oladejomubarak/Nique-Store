package oladejo.mubarak.niquestore.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class Scheduler {
    private final ConfirmationTokenService confirmationTokenService;
    private final Scheduler scheduler;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredToken(){
        System.out.println("TOKEN DELETED");
        confirmationTokenService.deleteExpiredToken();
    }
}
