package oladejo.mubarak.niquestore.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import oladejo.mubarak.niquestore.data.model.AppUser;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private String productName;
    private String category;
    private String vendorEmail;
    private double price;
    private int quantity;
    private String productImageUrl;
    private String description;
    private String storeAddress;
}
