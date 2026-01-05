package com.mycompany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * KafkaProducerService - Sends messages to Kafka topics
 * 
 * This service publishes events to Kafka when something happens
 * in the e-commerce service (like when an order is created).
 */
@Service
public class KafkaProducerService {

    // Kafka topic name
    private static final String TOPIC = "order-events";

    // KafkaTemplate is provided by Spring Kafka - handles sending messages
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Sends an order creation event to Kafka
     * 
     * @param orderId The ID of the order that was created
     * @param userId The ID of the user who created the order
     * @param total The total amount of the order
     */
    public void sendOrderCreatedEvent(Long orderId, Long userId, String total) {
        // Create a JSON-like message
        String message = String.format(
            "{\"eventType\":\"ORDER_CREATED\",\"orderId\":%d,\"userId\":%d,\"total\":\"%s\",\"timestamp\":\"%s\"}",
            orderId, userId, total, java.time.LocalDateTime.now()
        );
        
        // Send message to Kafka topic
        kafkaTemplate.send(TOPIC, message);
        
        System.out.println("📤 Sent order event to Kafka: " + message);
    }
}

