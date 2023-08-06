package oladejo.mubarak.niquestore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.niquestore.data.dto.request.ProductDto;
import oladejo.mubarak.niquestore.data.model.AppUser;
import oladejo.mubarak.niquestore.data.model.Product;
import oladejo.mubarak.niquestore.data.model.Role;
import oladejo.mubarak.niquestore.exception.NiqueStoreException;
import oladejo.mubarak.niquestore.repository.ProductRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{
    private final UserServiceImpl userService;
    private final ProductRepo productRepo;

    @Override
    public Product addProduct(ProductDto productDto) {
        AppUser foundUser = userService.findByEmail(productDto.getVendorEmail());
        if(!foundUser.getRole().contains(Role.VENDOR)) {throw new NiqueStoreException("Only vendors can add product");
        }
        Product product = new Product();
        product.setName(productDto.getProductName());
        product.setCategory(productDto.getCategory());
        product.setPrice(BigDecimal.valueOf(productDto.getPrice()));
        product.setQuantity(productDto.getQuantity());
        product.setStoreAddress(productDto.getStoreAddress());
        return productRepo.save(product);
    }

    @Override
    public Product findProduct(String productId) {
        return null;
    }

    @Override
    public Product editProduct(String productId, ProductDto productDto) {
        return null;
    }

    @Override
    public String deleteProduct(String productId) {
        return null;
    }

    @Override
    public List<Product> searchProductByName(String productName) {
        return null;
    }

    @Override
    public List<Product> searchProductByCategory(String category) {
        return null;
    }

    @Override
    public List<Product> findProductByVendor(String vendorEmail) {
        return null;
    }

    @Override
    public List<Product> findProductByKeyword(String keyword) {
        return null;
    }
}