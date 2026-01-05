# 📊 Database Guide - Simple Overview

## 🗂️ Your Database Tables (Small & Simple!)

### 1. **users** Table
Stores customer information.

**Columns:**
- `id` - Unique ID (1, 2, 3...)
- `name` - Customer name
- `email` - Customer email

**Current Data:**
```
id | name       | email
---|------------|------------------
1  | Sowri      | (null)
2  | Test User  | test@example.com
```

**What it means:** You have 2 customers in your system.

---

### 2. **products** Table
Stores product catalog.

**Columns:**
- `id` - Unique ID
- `name` - Product name
- `description` - Product description
- `price` - Product price
- `stock` - How many in stock

**Current Data:**
```
id | name          | price  | stock
---|---------------|--------|-------
1  | Test Product  | 99.99  | 8
```

**What it means:** You have 1 product. It costs $99.99 and you have 8 in stock.

---

### 3. **orders** Table
Stores customer orders.

**Columns:**
- `id` - Unique order ID
- `order_date` - When order was placed
- `total` - Total amount
- `status` - Order status (PENDING, CONFIRMED, etc.)
- `user_id` - Which user made this order (links to users table)

**Current Data:**
```
id | order_date              | total  | status  | user_id
---|-------------------------|--------|---------|--------
1  | 2026-01-04 18:18:22     | 199.98 | PENDING | 1
```

**What it means:** 
- User #1 (Sowri) placed 1 order
- Order total: $199.98
- Status: PENDING (waiting to be processed)

---

### 4. **order_items** Table
Stores what products are in each order.

**Columns:**
- `id` - Unique ID
- `quantity` - How many of this product
- `price` - Price at time of purchase
- `order_id` - Which order (links to orders table)
- `product_id` - Which product (links to products table)

**Current Data:**
```
id | quantity | price | order_id | product_id
---|----------|-------|----------|------------
1  | 2        | 99.99 | 1         | 1
```

**What it means:**
- Order #1 contains 2 units of Product #1
- Each unit cost $99.99
- Total: 2 × $99.99 = $199.98 ✅

---

## 🔗 How Tables Connect (Relationships)

```
User (id=1, name="Sowri")
  ↓ (has many orders)
Order (id=1, total=199.98, user_id=1)
  ↓ (has many items)
OrderItem (quantity=2, product_id=1)
  ↓ (references)
Product (id=1, name="Test Product", price=99.99)
```

**In Simple Terms:**
1. **User** makes an **Order**
2. **Order** contains **OrderItems**
3. Each **OrderItem** is a **Product**

---

## 📝 Quick SQL Queries to Understand Data

### See all users:
```sql
SELECT * FROM users;
```

### See all products:
```sql
SELECT * FROM products;
```

### See all orders with user names:
```sql
SELECT o.id, o.total, o.status, u.name as customer_name
FROM orders o
JOIN users u ON o.user_id = u.id;
```

### See order details (what products are in each order):
```sql
SELECT 
    o.id as order_id,
    o.total,
    p.name as product_name,
    oi.quantity,
    oi.price
FROM orders o
JOIN order_items oi ON o.id = oi.order_id
JOIN products p ON oi.product_id = p.id;
```

---

## 🧹 Keep Data Small (Learning Mode)

### Delete all test data:
```sql
-- Delete all orders (this will also delete order_items due to cascade)
DELETE FROM orders;

-- Delete all products
DELETE FROM products;

-- Delete all users
DELETE FROM users;
```

### Start fresh with simple data:
```sql
-- Add one user
INSERT INTO users (name, email) VALUES ('John Doe', 'john@example.com');

-- Add one product
INSERT INTO products (name, description, price, stock) 
VALUES ('Laptop', 'Gaming Laptop', 999.99, 10);

-- Create one order
INSERT INTO orders (order_date, total, status, user_id)
VALUES (NOW(), 999.99, 'PENDING', 1);

-- Add one order item
INSERT INTO order_items (quantity, price, order_id, product_id)
VALUES (1, 999.99, 1, 1);
```

---

## 💡 Key Concepts

1. **Primary Key (`id`)**: Unique identifier for each row
2. **Foreign Key (`user_id`, `order_id`, `product_id`)**: Links tables together
3. **Relationships**:
   - One User → Many Orders (1-to-Many)
   - One Order → Many OrderItems (1-to-Many)
   - One Product → Many OrderItems (1-to-Many)

---

## 🎯 Current Database Summary

- **2 users** (customers)
- **1 product** (in catalog)
- **1 order** (placed by user #1)
- **1 order item** (2 units of product #1)

**Total:** Very small dataset - perfect for learning! 📚

