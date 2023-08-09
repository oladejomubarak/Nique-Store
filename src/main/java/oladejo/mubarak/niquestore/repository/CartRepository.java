package oladejo.mubarak.niquestore.repository;

import oladejo.mubarak.niquestore.data.model.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<Cart, String> {

}
