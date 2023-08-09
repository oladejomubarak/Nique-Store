package oladejo.mubarak.niquestore.data.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class Cart {
    @Id
    private String id;
    @DBRef
    private AppUser appUser;
    @DBRef
    private List<Order> orderList = new ArrayList<>();
    private BigDecimal amountToPay;
    private LocalDate deliveryDate;
}
