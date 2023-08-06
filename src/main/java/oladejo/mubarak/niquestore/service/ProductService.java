package oladejo.mubarak.niquestore.service;


import oladejo.mubarak.niquestore.data.dto.request.ProductDto;
import oladejo.mubarak.niquestore.data.model.Product;

public interface ProductService {
    Product addProduct(ProductDto productDto);
}
