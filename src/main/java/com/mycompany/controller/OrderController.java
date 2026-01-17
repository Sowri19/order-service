package com.mycompany.controller;

import com.mycompany.dto.request.CreateOrderRequest;
import com.mycompany.dto.response.OrderItemResponse;
import com.mycompany.dto.response.OrderResponse;
import com.mycompany.dto.request.UpdateStatusRequest;
import com.mycompany.model.Order;
import com.mycompany.model.OrderItem;
import com.mycompany.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * OrderController - REST API endpoints for Order operations
 * 
 * This controller handles HTTP requests for order management:
 * - GET /api/orders - Get all orders
 * - GET /api/orders/{id} - Get a specific order
 * - GET /api/orders/user/{userId} - Get all orders for a user
 * - POST /api/orders - Create a new order
 * - PUT /api/orders/{id}/status - Update order status
 */
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    // Dependency Injection: Spring automatically provides service instance
    @Autowired
    private OrderService orderService;

    /**
     * GET /api/orders
     * Returns all orders in the database
     */
    @GetMapping
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders().stream()
            .map(this::toOrderResponse)
            .toList();
    }

    /**
     * GET /api/orders/{id}
     * Returns a specific order by ID
     * Returns 404 if order not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderService.getOrderById(id);
        
        return order.map(value -> ResponseEntity.ok(toOrderResponse(value)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * GET /api/orders/user/{userId}
     * Returns all orders for a specific user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUser(@PathVariable Long userId) {
        Optional<List<Order>> orders = orderService.getOrdersByUser(userId);
        return orders.map(value -> ResponseEntity.ok(value.stream().map(this::toOrderResponse).toList()))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/orders
     * Creates a new order
     * Request body should contain:
     * {
     *   "userId": 1,
     *   "items": [
     *     {"productId": 1, "quantity": 2},
     *     {"productId": 2, "quantity": 1}
     *   ]
     * }
     */
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        Optional<Order> savedOrder = orderService.createOrder(request);
        if (savedOrder.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(toOrderResponse(savedOrder.get()));
    }

    /**
     * PUT /api/orders/{id}/status
     * Updates the status of an order
     * Request body should contain: "status": "CONFIRMED" (or other status)
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest request) {
        Optional<Order> updatedOrder = orderService.updateOrderStatus(id, request.getStatus());
        return updatedOrder.map(value -> ResponseEntity.ok(toOrderResponse(value)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private OrderResponse toOrderResponse(Order order) {
        Long userId = order.getUser() != null ? order.getUser().getId() : null;
        List<OrderItem> orderItems = order.getOrderItems() != null ? order.getOrderItems() : Collections.emptyList();
        List<OrderItemResponse> items = orderItems.stream()
            .map(item -> new OrderItemResponse(
                item.getProduct() != null ? item.getProduct().getId() : null,
                item.getQuantity(),
                item.getPrice()
            ))
            .toList();
        return new OrderResponse(
            order.getId(),
            userId,
            order.getTotal(),
            order.getStatus(),
            order.getOrderDate(),
            items
        );
    }
}
