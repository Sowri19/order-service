package com.mycompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Application Class - Entry point of the Spring Boot application
 * 
 * @SpringBootApplication is a convenience annotation that combines:
 * - @Configuration: Marks this class as a configuration class
 * - @EnableAutoConfiguration: Enables Spring Boot auto-configuration
 * - @ComponentScan: Scans for components, services, repositories, etc.
 * 
 * When you run this application, Spring Boot will:
 * 1. Start an embedded Tomcat server
 * 2. Connect to the PostgreSQL database (configured in application.properties)
 * 3. Create database tables based on your @Entity classes
 * 4. Make your REST API endpoints available
 */
@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
        
        System.out.println("\n🚀 ========================================");
        System.out.println("     Order Service Started Successfully!");
        System.out.println("========================================\n");
        System.out.println("📡 Server: http://localhost:8080");
        System.out.println("\n📋 Available API Endpoints:");
        System.out.println("   Users:");
        System.out.println("     GET    /api/users          - Get all users");
        System.out.println("     GET    /api/users/{id}      - Get user by ID");
        System.out.println("     POST   /api/users           - Create user");
        System.out.println("     PUT    /api/users/{id}      - Update user");
        System.out.println("     DELETE /api/users/{id}      - Delete user");
        System.out.println("\n   Products:");
        System.out.println("     GET    /api/products       - Get all products");
        System.out.println("     GET    /api/products/{id}   - Get product by ID");
        System.out.println("     POST   /api/products        - Create product");
        System.out.println("     PUT    /api/products/{id}   - Update product");
        System.out.println("     DELETE /api/products/{id}   - Delete product");
        System.out.println("\n   Orders:");
        System.out.println("     GET    /api/orders          - Get all orders");
        System.out.println("     GET    /api/orders/{id}     - Get order by ID");
        System.out.println("     GET    /api/orders/user/{userId} - Get user's orders");
        System.out.println("     POST   /api/orders          - Create order");
        System.out.println("     PUT    /api/orders/{id}/status - Update order status");
        System.out.println("\n💡 Use Postman or curl to test the API");
        System.out.println("========================================\n");
    }
}
