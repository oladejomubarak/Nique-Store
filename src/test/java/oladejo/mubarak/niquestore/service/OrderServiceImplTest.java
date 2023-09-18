package oladejo.mubarak.niquestore.service;

import oladejo.mubarak.niquestore.data.dto.request.OrderProductRequest;
import oladejo.mubarak.niquestore.data.model.Order;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;

    private OrderProductRequest orderProductRequest;

    @BeforeEach
    void setUp() {
        orderProductRequest = new OrderProductRequest();
        orderProductRequest.setCustomerEmail("oladejomubarakade@gmail.com");
        orderProductRequest.setProductId("64efa68aa1b41925d2d4aebc");
        orderProductRequest.setQuantity(2);
    }
     @Test
     void testThatProductCanBeOrdered(){
        Order order = orderService.orderProduct(orderProductRequest);
        assertEquals(BigDecimal.valueOf(10000.0), order.getTotalPrice());
    }
    @Test void testThatOrderCanBeFound(){
        Order order = orderService.findOrder("6508b0cf5a11e20ec877dceb");
        assertEquals(BigDecimal.valueOf(10000.0), order.getTotalPrice());
    }
}