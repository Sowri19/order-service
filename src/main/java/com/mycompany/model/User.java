package com.mycompany.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User Entity - Represents a customer in the e-commerce system
 * 
 * This class is a JPA Entity that maps to a "users" table in the database.
 * It has a one-to-many relationship with Order (one user can have many orders).
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    // One user can have many orders
    // CascadeType.ALL means if we delete a user, all their orders are deleted too
    // mappedBy = "user" means the Order entity has a "user" field that owns this relationship
    // @JsonIgnore prevents circular reference - when serializing user, don't include orders
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    // Default constructor required by JPA
    public User() {
    }

    // Constructor for creating a user with name and email
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
