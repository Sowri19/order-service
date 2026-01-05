package com.mycompany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * OrderItemRepository - Data Access Layer for OrderItem entity
 * 
 * Provides database operations for order items.
 * Spring Data JPA automatically implements all CRUD methods.
 */
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Custom query methods can be added here if needed
}

