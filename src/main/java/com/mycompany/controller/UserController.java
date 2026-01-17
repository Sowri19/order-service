package com.mycompany.controller;

import com.mycompany.dto.response.UserResponse;
import com.mycompany.model.User;
import com.mycompany.service.UserService;
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

    // Dependency Injection: Spring automatically provides UserService instance
    @Autowired
    private UserService userService;

    /**
     * GET /api/users
     * Returns all users in the database
     */
    @GetMapping
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers().stream()
            .map(this::toUserResponse)
            .toList();
    }

    /**
     * GET /api/users/{id}
     * Returns a specific user by ID
     * Returns 404 if user not found
     */
    @GetMapping("/{id}")
    @Transactional(readOnly = true)
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        
        return user.map(value -> ResponseEntity.ok(toUserResponse(value)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/users
     * Creates a new user
     * Request body should contain: name, email
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(toUserResponse(savedUser));
    }

    /**
     * PUT /api/users/{id}
     * Updates an existing user
     * Returns 404 if user not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> updatedUser = userService.updateUser(id, userDetails);
        return updatedUser.map(value -> ResponseEntity.ok(toUserResponse(value)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/users/{id}
     * Deletes a user by ID
     * Returns 404 if user not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }
}
