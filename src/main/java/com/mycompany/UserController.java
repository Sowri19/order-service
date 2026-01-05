package com.mycompany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

/**
 * UserController - REST API endpoints for User operations
 * 
 * This controller handles HTTP requests for user management:
 * - GET /api/users - Get all users
 * - GET /api/users/{id} - Get a specific user
 * - POST /api/users - Create a new user
 * - PUT /api/users/{id} - Update a user
 * - DELETE /api/users/{id} - Delete a user
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    // Dependency Injection: Spring automatically provides UserRepository instance
    @Autowired
    private UserRepository userRepository;

    /**
     * GET /api/users
     * Returns all users in the database
     */
    @GetMapping
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * GET /api/users/{id}
     * Returns a specific user by ID
     * Returns 404 if user not found
     */
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/users
     * Creates a new user
     * Request body should contain: name, email
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    /**
     * PUT /api/users/{id}
     * Updates an existing user
     * Returns 404 if user not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> optionalUser = userRepository.findById(id);
        
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            
            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/users/{id}
     * Deletes a user by ID
     * Returns 404 if user not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
