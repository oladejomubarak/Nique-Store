package oladejo.mubarak.niquestore.service;

import oladejo.mubarak.niquestore.data.dto.request.OrderProductRequest;
import oladejo.mubarak.niquestore.data.model.Order;

import java.math.BigDecimal;
import java.util.List;

public interface OrderService {
  Order orderProduct(OrderProductRequest orderProductRequest);
  Order findOrder(String orderId);
  void cancelOrder(String orderId);
  List<Order> addOrderToCart(OrderProductRequest orderProductRequest);
  void removeOrderFromCart(String customerEmail, String orderId);
  BigDecimal orderFromCart(String customerEmail);
}
