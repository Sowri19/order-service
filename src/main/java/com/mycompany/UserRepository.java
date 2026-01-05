package com.mycompany;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * UserRepository - Data Access Layer for User entity
 * 
 * This interface extends JpaRepository which provides CRUD operations automatically:
 * - save() - Create or update a user
 * - findById() - Find user by ID
 * - findAll() - Get all users
 * - delete() - Delete a user
 * 
 * Spring Data JPA automatically implements this interface at runtime.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom query methods here if needed
    // Example: User findByEmail(String email);
}
