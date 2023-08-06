package oladejo.mubarak.niquestore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.niquestore.data.dto.request.ProductDto;
import oladejo.mubarak.niquestore.data.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService{
    @Override
    public Product addProduct(ProductDto productDto) {
        return null;
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
