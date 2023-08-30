package oladejo.mubarak.niquestore.service;

public interface PaymentService {
    String initiatePaymentForCart(String customerEmail)throws Exception;
    String initiatePaymentForSingleOrder(String customerEmail, String orderId)throws Exception;
}
