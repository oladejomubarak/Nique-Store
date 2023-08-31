package oladejo.mubarak.niquestore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.niquestore.data.dto.request.OrderProductRequest;
import oladejo.mubarak.niquestore.data.model.*;
import oladejo.mubarak.niquestore.exception.NiqueStoreException;
import oladejo.mubarak.niquestore.repository.OrderRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final UserServiceImpl userService;
    private final ProductServiceImpl productService;
    private final CartService cartService;
    private final OrderRepo orderRepo;
    @Override
    public Order orderProduct(OrderProductRequest orderProductRequest) {
        AppUser foundUser = userService.findByEmail(orderProductRequest.getCustomerEmail());
        Product foundProduct = productService.findProduct(orderProductRequest.getProductId());
        if(orderProductRequest.getQuantity() > foundProduct.getQuantity()) {throw new NiqueStoreException("" +
                "You ordered "+orderProductRequest.getQuantity()+" quantities, but only "+foundProduct.getQuantity()+" are left");
        }
        BigDecimal totalPrice = foundProduct.getPrice().multiply(BigDecimal.valueOf(orderProductRequest.getQuantity()));

        Order order = new Order();
        order.setUser(foundUser);
        order.setProduct(foundProduct);
        order.setDeliveryDate(LocalDate.now().plusDays(1));
        order.setQuantity(orderProductRequest.getQuantity());
        order.setTotalPrice(totalPrice);
        order.setPaymentStatus(PaymentStatus.PENDING);
        if(totalPrice.compareTo(BigDecimal.valueOf(50000)) >= 0){
            order.setTotalPrice(getDiscount(totalPrice));
        }
        foundProduct.setQuantity(foundProduct.getQuantity() - orderProductRequest.getQuantity());
        productService.saveProduct(foundProduct);
        return orderRepo.save(order);
    }

    @Override
    public Order findOrder(String orderId) {
        return orderRepo.findById(orderId).orElseThrow(()-> new NiqueStoreException("order not found"));
    }

    @Override
    public void cancelOrder(String orderId) {
        Order foundOrder = findOrder(orderId);
        if(foundOrder.getDeliveryDate().equals(LocalDate.now())) {throw new NiqueStoreException("" +
                "You can't cancel order on the delivery date");}
        for(Product product: productService.findAllProducts()){
            if(product.equals(foundOrder.getProduct())){
                product.setQuantity(product.getQuantity() + foundOrder.getQuantity());
                productService.saveProduct(product);
            }
        }
        orderRepo.delete(foundOrder);
    }

    @Override
    public List<Order> findAllOrders() {
        return orderRepo.findAll();
    }

    @Override
    public List<Order> addOrderToCart(OrderProductRequest orderProductRequest) {
        AppUser foundUser = userService.findByEmail(orderProductRequest.getCustomerEmail());
        Product foundProduct = productService.findProduct(orderProductRequest.getProductId());
        if(orderProductRequest.getQuantity() > foundProduct.getQuantity()) {throw new NiqueStoreException("" +
                "You ordered "+orderProductRequest.getQuantity()+" quantities, but only "+foundProduct.getQuantity()+" are left");
        }
        foundProduct.setVendor(null);
        Order order = new Order();
        order.setUser(foundUser);
        order.setProduct(foundProduct);
        order.setQuantity(orderProductRequest.getQuantity());
        order.setPaymentStatus(PaymentStatus.PENDING);
        order.setTotalPrice(foundProduct.getPrice().multiply(BigDecimal.valueOf(orderProductRequest.getQuantity())));
        orderRepo.save(order);
        order.setUser(null);
        if(foundUser.getCart() == null){
            Cart cart = new Cart();
            cartService.saveCart(cart);
            foundUser.setCart(cart);
            userService.saveUser(foundUser);
        }
        foundUser.getCart().getOrderList().add(order);
        //foundUser.getCart().setAmountToPay(order.getTotalPrice());
        userService.saveUser(foundUser);
        return foundUser.getCart().getOrderList();
    }
    @Override
    public void removeOrderFromCart(String customerEmail, String orderId) {
        AppUser foundUser = userService.findByEmail(customerEmail);
        Order foundOder = findOrder(orderId);
        for(Product product: productService.findAllProducts()){
            if(product.equals(foundOder.getProduct())){
                product.setQuantity(product.getQuantity() + foundOder.getQuantity());
                productService.saveProduct(product);
            }
        }

        foundUser.
                getCart()
                .getOrderList()
                .forEach(order -> {
            if(order.equals(foundOder)) foundUser.getCart().getOrderList().remove(foundOder);
            orderRepo.delete(foundOder);
            userService.saveUser(foundUser);
        });
    }

    @Override
    public BigDecimal checkout(String customerEmail) {
        AppUser foundUser = userService.findByEmail(customerEmail);

//        BigDecimal totalPrice = BigDecimal.ZERO;
//        for(Order order: foundUser.getCart().getOrderList()){
//            totalPrice = totalPrice.add(order.getTotalPrice());
//        }
//        foundUser.getCart().setAmountToPay(totalPrice);

        BigDecimal sumOfAmountToPay = foundUser.getCart().getOrderList().stream()
                .map(Order::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        foundUser.getCart().setAmountToPay(sumOfAmountToPay);
        userService.saveUser(foundUser);


        foundUser.getCart().setDeliveryDate(LocalDate.now().plusDays(1));
        foundUser.getCart().setPaymentStatus(PaymentStatus.PENDING);
        if(foundUser.getCart().getAmountToPay().compareTo(BigDecimal.valueOf(50000)) >= 0 ){
            foundUser.getCart().setAmountToPay(getDiscount(foundUser.getCart().getAmountToPay()));
            userService.saveUser(foundUser);
        }
        return foundUser.getCart().getAmountToPay();
    }

    @Override
    public void saveOrder(Order order) {
        orderRepo.save(order);
    }

    private BigDecimal getDiscount(BigDecimal amount){
        BigDecimal percent = new BigDecimal("0.03");
        BigDecimal discount = amount.multiply(percent);
        return amount.subtract(discount);
    }
}
