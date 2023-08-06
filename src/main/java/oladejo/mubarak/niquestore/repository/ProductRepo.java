package oladejo.mubarak.niquestore.repository;

import oladejo.mubarak.niquestore.data.model.AppUser;
import oladejo.mubarak.niquestore.data.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepo extends MongoRepository<Product, String> {
    List<Product> findByNameIgnoreCase(String productName);
    List<Product> findByCategoryIgnoreCase(String productCategory);
    List<Product> findByVendor(AppUser vendor);

}
