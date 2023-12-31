package oladejo.mubarak.niquestore.service;

import com.squareup.okhttp.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.niquestore.config.email.EmailServiceImpl;
import oladejo.mubarak.niquestore.data.model.AppUser;
import oladejo.mubarak.niquestore.data.model.Order;
import oladejo.mubarak.niquestore.data.model.PaymentStatus;
import oladejo.mubarak.niquestore.exception.NiqueStoreException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final EmailServiceImpl emailService;
    private final OrderServiceImpl orderService;
    private final UserServiceImpl userService;
    private final String pay_stack_key = System.getenv("PAY_STACK_SECRET_KEY");

    private final  OkHttpClient client = new OkHttpClient();

    private final MediaType mediaType = MediaType.parse("application/json");
    @Override
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
        emailService.sendPaymentEmail(foundUser.getEmail(),buildPaymentEmail(foundUser.getFirstname(),
                paymentDetails,
                foundUser.getCart().getDeliveryDate()));
        foundUser.getCart().setPaymentStatus(PaymentStatus.SUCCESS);
        for (Order order:foundUser.getCart().getOrderList()){
            order.setPaymentStatus(PaymentStatus.SUCCESS);
            orderService.saveOrder(order);
        }
        foundUser.getCart().getOrderList().clear();
        userService.saveUser(foundUser);

        return "Payment initiated, please check your email to complete your payment";
    }
    @Override
    public String initiatePaymentForSingleOrder(String customerEmail, String orderId)throws Exception{
        Order foundOrder = orderService.findOrder(orderId);
        if(foundOrder.getPaymentStatus().equals(PaymentStatus.SUCCESS)){
            throw new NiqueStoreException("This order has already been paid for");
        }
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
        emailService.sendPaymentEmail(foundCustomer.getEmail(),buildPaymentEmail(foundCustomer.getFirstname(),
                paymentDetails,
                foundOrder.getDeliveryDate()));
        foundOrder.setPaymentStatus(PaymentStatus.SUCCESS);
        orderService.saveOrder(foundOrder);
        return "Payment initiated, please check your email to complete your payment";
    }

    private String buildPaymentEmail(String firstname, String paymentDetails, LocalDate deliveryDate){
        return "Here is your payment details" +
                "                                   " +
                "                                       " +
                "<p>Hello \"" + firstname + "\",</p>" +
                "<p>Your payment was successfully processed</p>" +
                "<p>Click the link provided below to complete your payment" +
                "<p>\"" + paymentDetails + "\"</p>" +
                "<br>" +
                "<p>Your order will be delivered on \"" + deliveryDate + "\"</p>" +
                "<p>Thank You!</p>";
    }
}
