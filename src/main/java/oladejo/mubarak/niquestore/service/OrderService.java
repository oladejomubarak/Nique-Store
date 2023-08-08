package oladejo.mubarak.niquestore.service;

import oladejo.mubarak.niquestore.data.dto.request.OrderProductRequest;
import oladejo.mubarak.niquestore.data.model.Order;

public interface OrderService {
  Order orderProduct(OrderProductRequest orderProductRequest);
  Order findOrder(String orderId);
  void cancelOrder(String orderId);
}
