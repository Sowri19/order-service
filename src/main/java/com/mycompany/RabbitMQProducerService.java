package com.mycompany;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * RabbitMQProducerService - Sends messages to RabbitMQ
 * 
 * This service publishes events to RabbitMQ when something happens
 * in the e-commerce service (like when an order is created).
 * 
 * This is similar to KafkaProducerService but uses RabbitMQ instead.
 * Good for learning: Compare Kafka vs RabbitMQ!
 */
@Service
public class RabbitMQProducerService {

    // RabbitMQ exchange name (like a topic in Kafka)
    private static final String EXCHANGE = "order-exchange";
    
    // RabbitMQ routing key (like a topic name in Kafka)
    private static final String ROUTING_KEY = "order.created";

    // RabbitTemplate is provided by Spring AMQP - handles sending messages
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * Sends an order creation event to RabbitMQ
     * 
     * @param orderId The ID of the order that was created
     * @param userId The ID of the user who created the order
     * @param total The total amount of the order
     */
    public void sendOrderCreatedEvent(Long orderId, Long userId, String total) {
        // Create a JSON-like message
        String message = String.format(
            "{\"eventType\":\"ORDER_CREATED\",\"orderId\":%d,\"userId\":%d,\"total\":\"%s\",\"timestamp\":\"%s\",\"broker\":\"RabbitMQ\"}",
            orderId, userId, total, java.time.LocalDateTime.now()
        );
        
        // Send message to RabbitMQ exchange
        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, message);
        
        System.out.println("🐰 Sent order event to RabbitMQ: " + message);
    }
}

