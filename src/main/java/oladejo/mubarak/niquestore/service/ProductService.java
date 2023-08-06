package oladejo.mubarak.niquestore.service;


import oladejo.mubarak.niquestore.data.dto.request.ProductDto;
import oladejo.mubarak.niquestore.data.model.Product;

import java.util.List;

public interface ProductService {
    Product addProduct(ProductDto productDto);
    Product findProduct(String productId);
    Product editProduct(String productId, ProductDto productDto);
    String deleteProduct(String productId);
    List<Product> searchProductByName(String productName);
    List<Product> searchProductByCategory(String category);

}
