package oladejo.mubarak.niquestore.service;

import lombok.RequiredArgsConstructor;
import oladejo.mubarak.niquestore.data.model.AppUser;
import oladejo.mubarak.niquestore.data.model.PaymentStatus;
import oladejo.mubarak.niquestore.data.model.Product;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OrderRetrieval {
    private final UserServiceImpl userService;
    private final ProductServiceImpl productService;
    private final OrderServiceImpl orderService;


    public void retrieveFailedOrder(){
        for(Product product: productService.findAllProducts()){
            orderService.findAllOrders().forEach(order -> {
                if( order.getProduct().equals(product) &&
                        order.getDeliveryDate().isBefore(LocalDate.now()) &&
                        order.getPaymentStatus().equals(PaymentStatus.PENDING)){
                    product.setQuantity(product.getQuantity() + order.getQuantity());

                    productService.saveProduct(product);
                }
            });

        }
    }

    public void retrieveFailedOrderFromCart(){
        for(Product product: productService.findAllProducts()){
            userService.findAllUsers().forEach(user -> user.getCart().getOrderList().forEach(order -> {
                if( product.equals(order.getProduct()) &&
                        user.getCart().getDeliveryDate().isBefore(LocalDate.now()) &&
                        user.getCart().getPaymentStatus().equals(PaymentStatus.PENDING))
                    product.setQuantity(product.getQuantity() + order.getQuantity());
                productService.saveProduct(product);
                user.getCart().getOrderList().clear();
                userService.saveUser(user);
            }));

        }
    }
}
