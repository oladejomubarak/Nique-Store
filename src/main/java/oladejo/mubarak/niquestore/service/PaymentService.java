package oladejo.mubarak.niquestore.service;

import com.squareup.okhttp.*;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.niquestore.config.email.EmailServiceImpl;
import oladejo.mubarak.niquestore.data.model.AppUser;
import oladejo.mubarak.niquestore.data.model.Order;
import oladejo.mubarak.niquestore.data.model.PaymentStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final EmailServiceImpl emailService;
    private final OrderServiceImpl orderService;
    private final UserServiceImpl userService;
    private final String pay_stack_key = System.getenv("PAY_STACK_SECRET_KEY");

    private final  OkHttpClient client = new OkHttpClient();

    private final MediaType mediaType = MediaType.parse("application/json");
    public String initiatePaymentForCart(String customerEmail) throws Exception{
        AppUser foundUser = userService.findByEmail(customerEmail);

        RequestBody body = RequestBody.create(mediaType,
                "{\"amount\":" + foundUser.getCart().getAmountToPay() + "," +
                        "\"email\":\"" + foundUser.getEmail() + "\"," +
                        "\"reference\":\"" + foundUser.getId() + "\"}");

        Request request = new Request.Builder()
                .url("https://api.paystack.co/transaction/initialize")
                .post(body)
                .addHeader("Authorization", "Bearer " + pay_stack_key)
                .addHeader("Content-Type", "application/json")
                .build();
            String paymentDetails = "";
        try (ResponseBody response = client.newCall(request).execute().body()) {
            paymentDetails += response.string();
        }
        emailService.send(foundUser.getEmail(),buildPaymentEmail(foundUser.getFirstname(),
                paymentDetails,
                foundUser.getCart().getDeliveryDate()));
        foundUser.getCart().setPaymentStatus(PaymentStatus.SUCCESS);
        foundUser.getCart().getOrderList().clear();
        userService.saveUser(foundUser);

        return "Payment Successful";
    }
    public String initiatePaymentForSingleOrder(String customerEmail, String orderId)throws Exception{
        Order foundOrder = orderService.findOrder(orderId);
        AppUser foundCustomer = userService.findByEmail(customerEmail);


        RequestBody paymentBody = RequestBody.create(mediaType,
                "{\"amount\":" + foundOrder .getTotalPrice() + "," +
                        "\"email\":\"" + foundCustomer.getEmail() + "\"," +
                        "\"reference\":\"" + foundOrder.getId() + "\"}");

        Request request = new Request.Builder()
                .url("https://api.paystack.co/transaction/initialize")
                .post(paymentBody)
                .addHeader("Authorization", "Bearer " + pay_stack_key)
                .addHeader("Content-Type", "application/json")
                .build();
        String paymentDetails = "";
        try (ResponseBody response = client.newCall(request).execute().body()) {
            paymentDetails += response.string();
        }
        emailService.send(foundCustomer.getEmail(),buildPaymentEmail(foundCustomer.getFirstname(),
                paymentDetails,
                foundOrder.getDeliveryDate()));
        foundOrder.setPaymentStatus(PaymentStatus.SUCCESS);
        return "Payment Successful";
    }

    private String buildPaymentEmail(String firstname, String paymentDetails, LocalDate deliveryDate){
        return "Here is your payment details" +
                "                                   " +
                "                                       " +
                "<p>Hello \"" + firstname + "\",</p>" +
                "<p>Your payment was successfully processed</p>" +
                "<p>Below is your payment details" +
                "<p>\"" + paymentDetails + "\"</p>" +
                "<br>" +
                "<p>Your order will be delivered on \"" + deliveryDate + "\"</p>" +
                "<p>Thank You!</p>";
    }
}
