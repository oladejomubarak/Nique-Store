package oladejo.mubarak.niquestore.service;


import oladejo.mubarak.niquestore.data.dto.request.ProductDto;
import oladejo.mubarak.niquestore.data.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductDto productDto);
    Product findProduct(String productId);
    Product editProduct(String productId, ProductDto productDto);
    void deleteProduct(String productId);
    void saveProduct(Product product);
    List<Product> findAllProducts();
    Page<Product> findProductsByPage(int pageNo, int pageSize);
    List<Product> searchProductByName(String productName);
    List<Product> searchProductByCategory(String category);
    List<Product> findProductByVendor(String vendorEmail);
    List<Product> findProductByKeyword(String keyword);

}
