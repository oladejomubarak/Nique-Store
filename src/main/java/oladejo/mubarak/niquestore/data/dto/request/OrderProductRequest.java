package oladejo.mubarak.niquestore.data.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderProductRequest {
    private String productId;
    private String customerEmail;
    private int quantity;
    private BigDecimal totalPrice;
}
