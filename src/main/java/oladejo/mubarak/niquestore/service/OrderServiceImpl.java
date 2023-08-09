package oladejo.mubarak.niquestore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.niquestore.data.dto.request.OrderProductRequest;
import oladejo.mubarak.niquestore.data.model.AppUser;
import oladejo.mubarak.niquestore.data.model.Order;
import oladejo.mubarak.niquestore.data.model.Product;
import oladejo.mubarak.niquestore.exception.NiqueStoreException;
import oladejo.mubarak.niquestore.repository.OrderRepo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    private final UserServiceImpl userService;
    private final ProductServiceImpl productService;
    private final OrderRepo orderRepo;
    @Override
    public Order orderProduct(OrderProductRequest orderProductRequest) {
        AppUser foundUser = userService.findByEmail(orderProductRequest.getCustomerEmail());
        Product foundProduct = productService.findProduct(orderProductRequest.getProductId());
        if(orderProductRequest.getQuantity() > foundProduct.getQuantity()) {throw new NiqueStoreException("" +
                "You ordered "+orderProductRequest.getQuantity()+", but only "+foundProduct.getQuantity()+" are left");
        }
        BigDecimal totalPrice = foundProduct.getPrice().multiply(BigDecimal.valueOf(orderProductRequest.getQuantity()));

        Order order = new Order();
        order.setUser(foundUser);
        order.setProduct(foundProduct);
        order.setDeliveryDate(LocalDate.now().plusDays(1));
        order.setQuantity(orderProductRequest.getQuantity());
        order.setTotalPrice(totalPrice);
        if(totalPrice.compareTo(BigDecimal.valueOf(50000)) >= 0){
            order.setTotalPrice(getDiscount(totalPrice));
        }
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
        orderRepo.delete(foundOrder);
    }

    private BigDecimal getDiscount(BigDecimal amount){
        BigDecimal percent = new BigDecimal("0.03");
        BigDecimal discount = amount.multiply(percent);
        return amount.subtract(discount);
    }
}
