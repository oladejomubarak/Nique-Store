package oladejo.mubarak.niquestore.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@Data
public class Order {
    @Id
    private String id;
    private String productName;
    private String productCategories;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal totalPrice;
}
