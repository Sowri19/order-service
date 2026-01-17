package com.mycompany.service.impl;

import com.mycompany.dto.request.CreateOrderRequest;
import com.mycompany.dto.request.OrderItemRequest;
import com.mycompany.model.Order;
import com.mycompany.model.OrderItem;
import com.mycompany.model.OrderStatus;
import com.mycompany.model.Product;
import com.mycompany.model.User;
import com.mycompany.repository.OrderRepository;
import com.mycompany.repository.ProductRepository;
import com.mycompany.repository.UserRepository;
import com.mycompany.service.KafkaProducerService;
import com.mycompany.service.OrderMetrics;
import com.mycompany.service.OrderService;
import com.mycompany.service.RabbitMQProducerService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

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

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Optional<List<Order>> getOrdersByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(orderRepository.findByUser(user.get()));
    }

    @Override
    public Optional<Order> createOrder(CreateOrderRequest request) {
        Optional<User> userOptional = userRepository.findById(request.getUserId());
        if (userOptional.isEmpty()) {
            return Optional.empty();
        }
        User user = userOptional.get();

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.PENDING);
        order.setOrderDate(java.time.LocalDateTime.now());

        BigDecimal total = BigDecimal.ZERO;

        for (OrderItemRequest itemRequest : request.getItems()) {
            Optional<Product> productOptional = productRepository.findById(itemRequest.getProductId());
            if (productOptional.isEmpty()) {
                return Optional.empty();
            }

            Product product = productOptional.get();

            if (product.getStock() < itemRequest.getQuantity()) {
                return Optional.empty();
            }

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity()));
            total = total.add(itemTotal);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setPrice(product.getPrice());

            order.getOrderItems().add(orderItem);

            product.setStock(product.getStock() - itemRequest.getQuantity());
            productRepository.save(product);
        }

        order.setTotal(total);
        Order savedOrder = orderRepository.save(order);

        kafkaProducerService.sendOrderCreatedEvent(
            savedOrder.getId(),
            user.getId(),
            savedOrder.getTotal().toString()
        );

        rabbitMQProducerService.sendOrderCreatedEvent(
            savedOrder.getId(),
            user.getId(),
            savedOrder.getTotal().toString()
        );

        orderMetrics.incrementOrdersCreated();

        return Optional.of(savedOrder);
    }

    @Override
    public Optional<Order> updateOrderStatus(Long id, OrderStatus status) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isEmpty()) {
            return Optional.empty();
        }
        Order order = orderOptional.get();
        order.setStatus(status);
        return Optional.of(orderRepository.save(order));
    }
}
