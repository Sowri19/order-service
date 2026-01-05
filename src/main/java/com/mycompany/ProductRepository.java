package com.mycompany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * ProductRepository - Data Access Layer for Product entity
 * 
 * Provides database operations for products.
 * Spring Data JPA automatically implements all CRUD methods.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Custom query methods can be added here
    // Example: List<Product> findByNameContaining(String name);
}

