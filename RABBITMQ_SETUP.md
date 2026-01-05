# 🐰 RabbitMQ Setup Complete!

## ✅ What's Installed

- **RabbitMQ Server** - Running on port **5672**
- **RabbitMQ Management UI** - Available at **http://localhost:15672**
  - Username: `guest`
  - Password: `guest`

---

## 🔌 Ports Summary

| Service | Port | URL |
|---------|------|-----|
| E-Commerce Service | 8080 | http://localhost:8080 |
| Notification Service | 8081 | http://localhost:8081 |
| Kafka | 9092 | localhost:9092 |
| **RabbitMQ** | **5672** | localhost:5672 |
| **RabbitMQ Management** | **15672** | http://localhost:15672 |
| PostgreSQL | 5432 | localhost:5432 |

---

## 🧪 Testing RabbitMQ

### **1. Create an Order (Sends to Both Kafka AND RabbitMQ):**
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"items":[{"productId":1,"quantity":1}]}'
```

### **2. Watch Notification Service Logs:**
```bash
tail -f /tmp/notification-rabbitmq.log
```

**You'll see BOTH messages:**
- `📨 Received Order Event from Kafka!`
- `🐰 Received Order Event from RabbitMQ!`

### **3. Check RabbitMQ Management UI:**
```bash
# Open in browser
open http://localhost:15672

# Or visit manually:
# http://localhost:15672
# Login: guest / guest
```

In the UI you can see:
- **Queues** - `order-queue` with message count
- **Exchanges** - `order-exchange`
- **Connections** - Active connections
- **Messages** - Message flow

### **4. Check RabbitMQ via Command Line:**
```bash
# List queues
rabbitmqctl list_queues name messages

# List exchanges
rabbitmqctl list_exchanges name type

# Check connections
rabbitmqctl list_connections
```

---

## 📁 Files Created

### **E-Commerce Service:**
- `RabbitMQProducerService.java` - Sends messages to RabbitMQ
- `RabbitMQConfig.java` - Configures exchange, queue, binding

### **Notification Service:**
- `RabbitMQOrderListener.java` - Receives messages from RabbitMQ
- `RabbitMQConfig.java` - Configures queue

---

## 🔄 How It Works

1. **Order created** → `OrderController.createOrder()`
2. **Sends to Kafka** → `KafkaProducerService.sendOrderCreatedEvent()`
3. **Sends to RabbitMQ** → `RabbitMQProducerService.sendOrderCreatedEvent()`
4. **RabbitMQ routes** → Exchange → Queue
5. **Notification service receives** → `RabbitMQOrderListener.handleOrderEvent()`

---

## 🎓 Key Concepts

### **Exchange:**
- Routes messages to queues
- Type: `TopicExchange` (routes based on routing key)
- Name: `order-exchange`

### **Queue:**
- Stores messages until consumed
- Name: `order-queue`
- Durable: Yes (survives server restart)

### **Routing Key:**
- Determines which queue gets the message
- Value: `order.created`
- Exchange uses this to route messages

### **Binding:**
- Links exchange to queue with a routing key
- Pattern: Exchange → (routing key) → Queue

---

## 🆚 Kafka vs RabbitMQ in Our Code

**Both are working simultaneously!** When you create an order:

```
Order Created
    ↓
    ├─→ Kafka (Topic: order-events)
    │   └─→ Notification Service (Kafka Consumer)
    │
    └─→ RabbitMQ (Exchange: order-exchange → Queue: order-queue)
        └─→ Notification Service (RabbitMQ Consumer)
```

**Result:** Notification service receives the message **twice** (once from each broker)!

---

## 🎯 Quick Commands

```bash
# Start RabbitMQ
brew services start rabbitmq

# Stop RabbitMQ
brew services stop rabbitmq

# Check status
rabbitmqctl status

# Open Management UI
open http://localhost:15672
```

---

## ✅ Everything is Ready!

- ✅ RabbitMQ installed and running
- ✅ Producer configured in e-commerce service
- ✅ Consumer configured in notification service
- ✅ Exchange, queue, and binding created
- ✅ Both Kafka and RabbitMQ working side by side!

**Perfect for learning and comparing!** 🎉

