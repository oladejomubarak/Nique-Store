package oladejo.mubarak.niquestore.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oladejo.mubarak.niquestore.data.dto.request.OrderProductRequest;
import oladejo.mubarak.niquestore.data.model.Order;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{
    @Override
    public Order orderProduct(OrderProductRequest orderProductRequest) {
        return null;
    }

    @Override
    public Order findOrder(String orderId) {
        return null;
    }

    @Override
    public void cancelOrder(String orderId) {

    }
}
