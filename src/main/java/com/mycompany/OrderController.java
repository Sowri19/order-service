package com.mycompany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
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

    // Dependency Injection: Spring automatically provides repository instances
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KafkaProducerService kafkaProducerService;

    @Autowired
    private RabbitMQProducerService rabbitMQProducerService;

    @Autowired
    private OrderMetrics orderMetrics;

    /**
     * GET /api/orders
     * Returns all orders in the database
     */
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * GET /api/orders/{id}
     * Returns a specific order by ID
     * Returns 404 if order not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        Optional<Order> order = orderRepository.findById(id);
        
        if (order.isPresent()) {
            return ResponseEntity.ok(order.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET /api/orders/user/{userId}
     * Returns all orders for a specific user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Order>> getOrdersByUser(@PathVariable Long userId) {
        Optional<User> user = userRepository.findById(userId);
        
        if (user.isPresent()) {
            List<Order> orders = orderRepository.findByUser(user.get());
            return ResponseEntity.ok(orders);
        } else {
            return ResponseEntity.notFound().build();
        }
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
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest request) {
        // Find the user
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        User user = userOptional.get();

        // Create the order
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(java.time.LocalDateTime.now());
        
        BigDecimal total = BigDecimal.ZERO;
        
        // Process each item in the order
        for (OrderItemRequest itemRequest : request.getItems()) {
            Optional<Product> productOptional = productRepository.findById(itemRequest.getProductId());
            if (productOptional.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            
            Product product = productOptional.get();
            
            // Check if enough stock is available
            if (product.getStock() < itemRequest.getQuantity()) {
                return ResponseEntity.badRequest().build();
            }
            
            // Calculate item total
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            total = total.add(itemTotal);
            
            // Create order item
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice());
            
            order.getOrderItems().add(orderItem);
            
            // Update product stock
            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);
        }
        
        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);
        
        // Send order creation event to Kafka (for notification service)
        kafkaProducerService.sendOrderCreatedEvent(
            savedOrder.getId(), 
            user.getId(), 
            savedOrder.getTotal().toString()
        );
        
        // Also send to RabbitMQ (for learning - compare with Kafka!)
        rabbitMQProducerService.sendOrderCreatedEvent(
            savedOrder.getId(), 
            user.getId(), 
            savedOrder.getTotal().toString()
        );

        orderMetrics.incrementOrdersCreated();
        
        return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
    }

    /**
     * PUT /api/orders/{id}/status
     * Updates the status of an order
     * Request body should contain: "status": "CONFIRMED" (or other status)
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody UpdateStatusRequest request) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            order.setStatus(request.getStatus());
            Order updatedOrder = orderRepository.save(order);
            return ResponseEntity.ok(updatedOrder);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

/**
 * DTO (Data Transfer Object) for creating an order
 * This class is used to receive order data from the HTTP request
 */
class CreateOrderRequest {
    private Long userId;
    private List<OrderItemRequest> items;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<OrderItemRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
}

/**
 * DTO for order item request
 */
class OrderItemRequest {
    private Long productId;
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

/**
 * DTO for updating order status
 */
class UpdateStatusRequest {
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
