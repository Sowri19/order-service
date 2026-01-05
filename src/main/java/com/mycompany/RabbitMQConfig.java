package com.mycompany;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQConfig - Configuration for RabbitMQ
 * 
 * This class sets up RabbitMQ exchanges, queues, and bindings.
 * Similar to creating a Kafka topic, but RabbitMQ uses exchanges and queues.
 */
@Configuration
public class RabbitMQConfig {

    // Exchange name (like a topic category in Kafka)
    public static final String EXCHANGE_NAME = "order-exchange";
    
    // Queue name (like a topic name in Kafka)
    public static final String QUEUE_NAME = "order-queue";
    
    // Routing key (used to route messages from exchange to queue)
    public static final String ROUTING_KEY = "order.created";

    /**
     * Creates a Topic Exchange
     * Exchanges route messages to queues based on routing keys
     */
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    /**
     * Creates a Queue
     * Queues store messages until they are consumed
     */
    @Bean
    public Queue orderQueue() {
        return new Queue(QUEUE_NAME, true); // true = durable (survives server restart)
    }

    /**
     * Binds the queue to the exchange with a routing key
     * This tells RabbitMQ: "Send messages with routing key 'order.created' to 'order-queue'"
     */
    @Bean
    public Binding orderBinding() {
        return BindingBuilder
            .bind(orderQueue())
            .to(orderExchange())
            .with(ROUTING_KEY);
    }
}

