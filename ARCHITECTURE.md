# E-Commerce Backend Architecture Guide

This document explains how all the files in this e-commerce backend connect together.

## 📁 Project Structure

```
src/main/java/com/mycompany/
├── App.java                    # Main application entry point
├── User.java                   # User entity (database table)
├── Product.java                # Product entity (database table)
├── Order.java                  # Order entity (database table)
├── OrderItem.java              # OrderItem entity (database table)
├── OrderStatus.java            # Enum for order statuses
├── UserRepository.java         # Database operations for User
├── ProductRepository.java      # Database operations for Product
├── OrderRepository.java        # Database operations for Order
├── OrderItemRepository.java    # Database operations for OrderItem
├── UserController.java         # REST API endpoints for User
├── ProductController.java      # REST API endpoints for Product
└── OrderController.java        # REST API endpoints for Order
```

## 🔗 How Everything Connects

### 1. **Application Flow**

```
HTTP Request → Controller → Repository → Database
                ↓
            Entity (Java Object)
```

### 2. **Layer Architecture**

#### **Layer 1: Entities (Database Tables)**
- **Purpose**: Represent database tables as Java classes
- **Files**: `User.java`, `Product.java`, `Order.java`, `OrderItem.java`
- **How it works**: 
  - `@Entity` annotation tells Spring this is a database table
  - `@Id` marks the primary key
  - `@Column` defines column properties
  - Relationships are defined with `@OneToMany`, `@ManyToOne`, etc.

**Example**: `User.java`
```java
@Entity                    // This class = database table
public class User {
    @Id                    // Primary key
    private Long id;
    
    @OneToMany            // One user has many orders
    private List<Order> orders;
}
```

#### **Layer 2: Repositories (Data Access)**
- **Purpose**: Handle all database operations (save, find, delete)
- **Files**: `UserRepository.java`, `ProductRepository.java`, etc.
- **How it works**:
  - Extends `JpaRepository<Entity, ID>` 
  - Spring automatically creates implementations
  - Provides methods like `save()`, `findById()`, `findAll()`, `delete()`

**Example**: `UserRepository.java`
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring automatically provides:
    // - save(User user)
    // - findById(Long id)
    // - findAll()
    // - deleteById(Long id)
}
```

#### **Layer 3: Controllers (REST API)**
- **Purpose**: Handle HTTP requests and return responses
- **Files**: `UserController.java`, `ProductController.java`, `OrderController.java`
- **How it works**:
  - `@RestController` marks it as a REST API controller
  - `@RequestMapping` sets the base URL
  - `@GetMapping`, `@PostMapping`, etc. define HTTP methods
  - Uses `@Autowired` to inject repositories

**Example**: `UserController.java`
```java
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;  // Spring injects this automatically
    
    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();    // Calls repository method
    }
}
```

### 3. **Entity Relationships**

```
User (1) ────< (Many) Order
                    │
                    │ (1)
                    │
                    └───< (Many) OrderItem ────> (Many) Product
```

**Explanation**:
- **One User** can have **Many Orders** (`@OneToMany` in User, `@ManyToOne` in Order)
- **One Order** can have **Many OrderItems** (`@OneToMany` in Order, `@ManyToOne` in OrderItem)
- **One Product** can be in **Many OrderItems** (`@ManyToOne` in OrderItem, `@ManyToOne` in Product)

### 4. **Dependency Injection (How Spring Connects Everything)**

Spring uses **Dependency Injection** to automatically connect components:

1. **@Autowired**: Tells Spring to automatically provide an instance
   ```java
   @Autowired
   private UserRepository userRepository;  // Spring creates and injects this
   ```

2. **How it works**:
   - Spring scans all classes with `@Repository`, `@Service`, `@Controller`
   - Creates instances (singletons) of these classes
   - When it sees `@Autowired`, it injects the matching instance

### 5. **Request Flow Example: Creating an Order**

```
1. Client sends: POST /api/orders
   {
     "userId": 1,
     "items": [{"productId": 1, "quantity": 2}]
   }

2. OrderController.createOrder() receives the request
   ↓
3. Controller uses @Autowired UserRepository to find the user
   ↓
4. Controller uses @Autowired ProductRepository to find products
   ↓
5. Controller creates Order and OrderItem entities
   ↓
6. Controller uses @Autowired OrderRepository.save() to save to database
   ↓
7. Database tables are updated (orders, order_items)
   ↓
8. Controller returns the saved Order as JSON response
```

### 6. **Database Configuration**

**File**: `src/main/resources/application.properties`

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/mydatabase
spring.jpa.hibernate.ddl-auto=update
```

- **spring.datasource.url**: Tells Spring where the database is
- **ddl-auto=update**: Automatically creates/updates tables based on `@Entity` classes
- When you start the app, Spring reads your entities and creates tables automatically!

### 7. **How Spring Boot Starts Everything**

**File**: `App.java`

```java
@SpringBootApplication  // This annotation does everything!
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```

**What happens when you run this**:
1. Spring scans all classes in `com.mycompany` package
2. Finds all `@Entity` classes → Creates database tables
3. Finds all `@Repository` interfaces → Creates implementations
4. Finds all `@RestController` classes → Registers HTTP endpoints
5. Starts embedded Tomcat server on port 8080
6. Your API is now live!

## 🎯 Key Concepts for Learning Java/Spring

### **Annotations** (Special instructions for Spring)
- `@Entity`: "This is a database table"
- `@Repository`: "This handles database operations"
- `@RestController`: "This handles HTTP requests"
- `@Autowired`: "Spring, please provide this for me"
- `@GetMapping`: "This method handles GET requests"

### **Interfaces vs Classes**
- **Repository interfaces**: You only define the methods, Spring implements them
- **Entity classes**: You define the structure, Spring uses it to create tables
- **Controller classes**: You write the logic, Spring calls it when requests come in

### **Dependency Injection**
- Instead of: `UserRepository repo = new UserRepository();`
- You use: `@Autowired private UserRepository repo;`
- Spring automatically provides the instance!

## 📝 Summary

1. **Entities** = Database tables (what data you store)
2. **Repositories** = Database operations (how you access data)
3. **Controllers** = REST API endpoints (how clients interact)
4. **Spring Boot** = Connects everything automatically using annotations
5. **application.properties** = Configuration (database connection, etc.)

Everything is connected through Spring's Dependency Injection system!

