package oladejo.mubarak.niquestore.data.model;

import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@Data
public class Product {
    @Id
    private String id;
    private String name;
    private String category;
    private String description;
    private AppUser vendor;
    private BigDecimal price;
    private int quantity;
    private String productImageUrl;
    private String storeAddress;
}
