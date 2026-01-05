package com.mycompany;

/**
 * OrderStatus Enum - Represents the possible statuses of an order
 */
public enum OrderStatus {
    PENDING,    // Order is being processed
    CONFIRMED,  // Order is confirmed
    SHIPPED,    // Order has been shipped
    DELIVERED,  // Order has been delivered
    CANCELLED   // Order was cancelled
}

