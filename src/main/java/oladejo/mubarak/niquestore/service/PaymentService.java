package oladejo.mubarak.niquestore.service;

import com.squareup.okhttp.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.niquestore.data.model.AppUser;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final UserServiceImpl userService;
private final String pay_stack_key = System.getenv("PAY_STACK_SECRET_KEY");

    public String initiatePayment(String customerEmail) throws Exception {
        AppUser foundUser = userService.findByEmail(customerEmail);
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
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

        try (ResponseBody response = client.newCall(request).execute().body()) {
            return response.string();
        }
    }
}
