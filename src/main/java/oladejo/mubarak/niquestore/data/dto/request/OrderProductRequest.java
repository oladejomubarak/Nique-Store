package oladejo.mubarak.niquestore.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductRequest {
    @NotBlank
    private String productId;
    @NotBlank
    private String customerEmail;
    @NotBlank
    private int quantity;
//    private BigDecimal totalPrice;
}
