package com.mycompany.service;

import com.mycompany.dto.request.CreateOrderRequest;
import com.mycompany.model.Order;
import com.mycompany.model.OrderStatus;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> getAllOrders();
    Optional<Order> getOrderById(Long id);
    Optional<List<Order>> getOrdersByUser(Long userId);
    Optional<Order> createOrder(CreateOrderRequest request);
    Optional<Order> updateOrderStatus(Long id, OrderStatus status);
}
