package oladejo.mubarak.niquestore.data.model;

import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Document
@Data
public class Order {
    @Id
    private String id;
    private Product product;
    private int quantity;
    private BigDecimal totalPrice;
    private LocalDate deliveryDate;
    private PaymentStatus paymentStatus;
    @DBRef
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser user;
}
