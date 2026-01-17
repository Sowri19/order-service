package com.mycompany.repository;

import com.mycompany.model.Order;
import com.mycompany.model.OrderStatus;
import com.mycompany.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * OrderRepository - Data Access Layer for Order entity
 * 
 * Provides database operations for orders.
 * Spring Data JPA automatically implements all CRUD methods.
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Find all orders for a specific user
    List<Order> findByUser(User user);
    
    // Find orders by status
    List<Order> findByStatus(OrderStatus status);
}
