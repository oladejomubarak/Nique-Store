package oladejo.mubarak.niquestore.controller;

import lombok.RequiredArgsConstructor;
import oladejo.mubarak.niquestore.data.dto.request.OrderProductRequest;
import oladejo.mubarak.niquestore.data.dto.response.ApiResponse;
import oladejo.mubarak.niquestore.exception.NiqueStoreException;
import oladejo.mubarak.niquestore.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payment/")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;



    @PostMapping("cart/{customerId}")
    @PatchMapping("cart/{customerId}")
    public ResponseEntity<?> payForCartOrder(@PathVariable String customerId){
        try{
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Payment successful")
                    .data(paymentService.initiatePaymentForCart(customerId))
                    .status(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("cart/{customerEmail}/{orderId}")
    @PatchMapping("cart/{customerEmail}/{orderId}")
    public ResponseEntity<?> payForSingleOrder(@PathVariable String customerEmail, @PathVariable String orderId){
        try{
            ApiResponse apiResponse = ApiResponse.builder()
                    .message("Payment successful")
                    .data(paymentService.initiatePaymentForSingleOrder(customerEmail, orderId))
                    .status(HttpStatus.OK.value())
                    .build();
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
