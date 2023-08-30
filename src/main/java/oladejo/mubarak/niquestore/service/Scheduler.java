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
    private final OrderRetrieval orderRetrieval;

    @Scheduled(cron = "0 0 0 * * *")
    public void deleteExpiredToken(){
        confirmationTokenService.deleteExpiredToken();
        System.out.println("TOKEN DELETED");
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void retrieveSingleOrder(){
        orderRetrieval.retrieveFailedOrder();
        System.out.println("Retrieve single order method called");
    }
    @Scheduled(cron = "0 0 0 * * *")
    public void retrieveOrderFromCart(){
        orderRetrieval.retrieveFailedOrderFromCart();
        System.out.println("Retrieve order from cart method called");
    }

}
