# 🎉 Complete Setup - Kafka + RabbitMQ

## ✅ Everything is Working!

You now have **TWO message brokers** running side by side for learning!

---

## 🔌 All Ports

| Service | Port | Status | Test Command |
|---------|------|--------|--------------|
| **E-Commerce Service** | **8080** | ✅ Running | `curl http://localhost:8080/api/users/1` |
| **Notification Service** | **8081** | ✅ Running | `curl http://localhost:8081/health` |
| **Kafka** | **9092** | ✅ Running | `kafka-topics --list --bootstrap-server localhost:9092` |
| **RabbitMQ** | **5672** | ✅ Running | `rabbitmqctl status` |
| **RabbitMQ Management** | **15672** | ✅ Running | `open http://localhost:15672` |
| **PostgreSQL** | **5432** | ✅ Running | `psql -h localhost -U sowri -d mydatabase` |

---

## 🧪 Test Both Message Brokers

### **Create an Order (Sends to BOTH Kafka AND RabbitMQ):**
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"items":[{"productId":1,"quantity":1}]}'
```

### **Watch Notification Service Logs:**
```bash
tail -f /tmp/notification-rabbitmq.log
```

**You'll see BOTH:**
```
📨 Received Order Event from Kafka!
🐰 Received Order Event from RabbitMQ!
```

---

## 📊 Architecture

```
                    Order Created
                         ↓
            ┌────────────┴────────────┐
            ↓                         ↓
        Kafka (9092)          RabbitMQ (5672)
            ↓                         ↓
    Topic: order-events    Exchange: order-exchange
            ↓                         ↓
    Notification Service    Queue: order-queue
    (Port 8081)                    ↓
                            Notification Service
                            (Port 8081)
```

---

## 🎓 Learning Comparison

### **Kafka:**
- **Concept:** Topics (like a log)
- **Port:** 9092
- **Code:** `kafkaTemplate.send("order-events", message)`
- **Consumer:** `@KafkaListener(topics = "order-events")`
- **Best for:** High throughput, event streaming

### **RabbitMQ:**
- **Concept:** Exchange → Queue (routing)
- **Port:** 5672 (Management: 15672)
- **Code:** `rabbitTemplate.convertAndSend("order-exchange", "order.created", message)`
- **Consumer:** `@RabbitListener(queues = "order-queue")`
- **Best for:** Task queues, complex routing

---

## 🎯 Quick Test Commands

### **Test User Endpoint:**
```bash
curl http://localhost:8080/api/users/1
```

### **Test Notification Service:**
```bash
curl http://localhost:8081/health
```

### **Test Kafka:**
```bash
# View messages
kafka-console-consumer --bootstrap-server localhost:9092 --topic order-events --from-beginning
```

### **Test RabbitMQ:**
```bash
# Open Management UI
open http://localhost:15672
# Login: guest / guest

# Or check queues
rabbitmqctl list_queues name messages
```

### **Test Complete Flow:**
```bash
# Create order (sends to both!)
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"items":[{"productId":1,"quantity":1}]}'

# Watch logs
tail -f /tmp/notification-rabbitmq.log
```

---

## 📁 Key Files

### **Kafka:**
- `KafkaProducerService.java` - Producer
- `OrderEventListener.java` - Consumer

### **RabbitMQ:**
- `RabbitMQProducerService.java` - Producer
- `RabbitMQOrderListener.java` - Consumer
- `RabbitMQConfig.java` - Configuration (exchange, queue, binding)

---

## 🎉 Summary

**You now have:**
- ✅ E-Commerce Service with REST API
- ✅ Notification Service (Microservice)
- ✅ Kafka (Message Broker #1)
- ✅ RabbitMQ (Message Broker #2)
- ✅ Both working simultaneously!
- ✅ Clean, simple JSON responses
- ✅ Perfect for learning!

**When you create an order, it sends to BOTH Kafka and RabbitMQ, and the notification service receives from BOTH!**

This is perfect for understanding the differences between message brokers! 🚀

