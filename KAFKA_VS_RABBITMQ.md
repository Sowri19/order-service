# 📚 Kafka vs RabbitMQ - Learning Comparison

## 🎯 Why Both?

We've set up **both Kafka and RabbitMQ** so you can:
- **Compare** how they work
- **Learn** the differences
- **Understand** when to use which one

---

## 🏗️ Architecture Comparison

### **Kafka Architecture:**
```
E-Commerce Service → Kafka Topic (order-events) → Notification Service
```

### **RabbitMQ Architecture:**
```
E-Commerce Service → Exchange (order-exchange) → Queue (order-queue) → Notification Service
```

---

## 📊 Key Differences

| Feature | Kafka | RabbitMQ |
|---------|-------|----------|
| **Type** | Distributed log/streaming platform | Message broker |
| **Port** | 9092 | 5672 (Management UI: 15672) |
| **Concept** | Topics | Exchanges + Queues |
| **Message Storage** | Persistent log (keeps messages) | Queue (consumed = deleted) |
| **Best For** | High throughput, event streaming | Task queues, request/reply |
| **Complexity** | More complex | Simpler |
| **Use Case** | Log aggregation, event sourcing | Background jobs, notifications |

---

## 🔍 How They Work in Our Code

### **Kafka (Simple):**
```java
// Producer
kafkaTemplate.send("order-events", message);

// Consumer
@KafkaListener(topics = "order-events", groupId = "notification-service-group")
public void handleOrderEvent(String message) { ... }
```

### **RabbitMQ (Exchange + Queue):**
```java
// Producer
rabbitTemplate.convertAndSend("order-exchange", "order.created", message);

// Consumer
@RabbitListener(queues = "order-queue")
public void handleOrderEvent(String message) { ... }
```

---

## 🧪 Testing Both

### **Test Kafka:**
```bash
# Create order (sends to both Kafka and RabbitMQ)
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"items":[{"productId":1,"quantity":1}]}'

# Check Kafka messages
kafka-console-consumer --bootstrap-server localhost:9092 --topic order-events --from-beginning
```

### **Test RabbitMQ:**
```bash
# Same order creation (sends to both!)
# Check RabbitMQ Management UI
open http://localhost:15672
# Login: guest / guest

# Or check notification service logs
tail -f /tmp/notification-service.log
```

---

## 📝 What Happens When You Create an Order

1. **Order created** in e-commerce service
2. **Message sent to Kafka** → Topic: `order-events`
3. **Message sent to RabbitMQ** → Exchange: `order-exchange`, Queue: `order-queue`
4. **Notification service receives from Kafka** → Prints: "📨 Received Order Event from Kafka!"
5. **Notification service receives from RabbitMQ** → Prints: "🐰 Received Order Event from RabbitMQ!"

**Both work at the same time!** You'll see both messages in the logs.

---

## 🎓 Learning Points

### **Kafka:**
- ✅ **Topics** = Categories of messages
- ✅ **Persistent** = Messages stay even after consumption
- ✅ **High throughput** = Can handle millions of messages
- ✅ **Good for** = Event streaming, log aggregation

### **RabbitMQ:**
- ✅ **Exchanges** = Route messages to queues
- ✅ **Queues** = Store messages until consumed
- ✅ **Routing keys** = Determine which queue gets the message
- ✅ **Good for** = Task queues, request/reply patterns

---

## 🔌 Ports Summary

| Service | Port | Purpose |
|---------|------|---------|
| E-Commerce Service | 8080 | Main API |
| Notification Service | 8081 | Receives messages |
| Kafka | 9092 | Message broker |
| RabbitMQ | 5672 | Message broker |
| RabbitMQ Management | 15672 | Web UI (guest/guest) |
| PostgreSQL | 5432 | Database |

---

## 🎯 When to Use Which?

### **Use Kafka when:**
- You need high throughput (millions of messages/second)
- You want to replay messages (they persist)
- You're doing event sourcing or log aggregation
- Multiple consumers need the same messages

### **Use RabbitMQ when:**
- You need simple message queuing
- You want a web UI for monitoring
- You need complex routing (different queues for different purposes)
- You're doing background job processing

---

## 🧪 Quick Test

```bash
# 1. Create an order (triggers both Kafka and RabbitMQ)
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"items":[{"productId":1,"quantity":1}]}'

# 2. Watch notification service logs (you'll see BOTH!)
tail -f /tmp/notification-service.log

# You should see:
# - "📨 Received Order Event from Kafka!"
# - "🐰 Received Order Event from RabbitMQ!"
```

---

## 📚 Files Created

### **Kafka:**
- `KafkaProducerService.java` - Sends to Kafka
- `OrderEventListener.java` - Receives from Kafka

### **RabbitMQ:**
- `RabbitMQProducerService.java` - Sends to RabbitMQ
- `RabbitMQOrderListener.java` - Receives from RabbitMQ
- `RabbitMQConfig.java` - Sets up exchange, queue, binding

---

## 🎉 Summary

**Both message brokers are now working!** When you create an order:
- ✅ Kafka receives the message
- ✅ RabbitMQ receives the message
- ✅ Notification service processes both

This is perfect for learning - you can see how both work side by side! 🚀

