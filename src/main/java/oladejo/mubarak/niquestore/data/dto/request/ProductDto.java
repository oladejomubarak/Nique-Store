package oladejo.mubarak.niquestore.data.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import oladejo.mubarak.niquestore.data.model.AppUser;

import java.math.BigDecimal;

@Data
public class ProductDto {
    @NotBlank
    private String productName;
    private String category;
    @NotBlank
    private String vendorEmail;
    @NotBlank
    private double price;
    @NotBlank
    private int quantity;
    private String productImageUrl;
    private String description;
    private String storeAddress;
}
