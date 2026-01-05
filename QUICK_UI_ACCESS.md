# 🖥️ Quick Access to Message Broker UIs

## ✅ RabbitMQ UI - Ready Now!

**Already installed and running!**

### **Access:**
```bash
open http://localhost:15672
```

**Login:**
- Username: `guest`
- Password: `guest`

**What to Check:**
1. Click **"Queues"** → See `order-queue`
2. Click **"Exchanges"** → See `order-exchange`
3. Click **"Connections"** → See active connections

---

## 📊 Kafka UI - Setup Instructions

### **Step 1: Start Docker Desktop**

Docker Desktop must be running first.

**Check if Docker is running:**
```bash
docker ps
```

**If error, start Docker:**
```bash
open -a Docker
```

Wait for Docker to fully start (whale icon in menu bar).

### **Step 2: Start Kafka UI (Kafdrop)**

Once Docker is running:

```bash
docker run -d \
  --name kafdrop \
  -p 9000:9000 \
  -e KAFKA_BROKERCONNECT=localhost:9092 \
  obsidiandynamics/kafdrop
```

### **Step 3: Access Kafka UI**

```bash
open http://localhost:9000
```

Or visit: **http://localhost:9000**

**No login needed!**

---

## 🧪 Test Both UIs

### **1. Create an Order:**
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"items":[{"productId":1,"quantity":1}]}'
```

### **2. Check RabbitMQ UI:**
- Go to http://localhost:15672
- Login: guest / guest
- Queues → `order-queue` → See messages

### **3. Check Kafka UI:**
- Go to http://localhost:9000
- Click `order-events` topic
- See messages appear

---

## 📊 All UIs Summary

| UI | URL | Login | Status |
|----|-----|-------|--------|
| **RabbitMQ** | http://localhost:15672 | guest/guest | ✅ Ready |
| **Kafka (Kafdrop)** | http://localhost:9000 | None | ⏳ Start Docker first |

---

## 🎯 Quick Commands

### **Start Kafka UI:**
```bash
# 1. Make sure Docker is running
docker ps

# 2. Start Kafdrop
docker run -d --name kafdrop -p 9000:9000 -e KAFKA_BROKERCONNECT=localhost:9092 obsidiandynamics/kafdrop

# 3. Open in browser
open http://localhost:9000
```

### **Stop Kafka UI:**
```bash
docker stop kafdrop
```

### **Restart Kafka UI:**
```bash
docker restart kafdrop
```

---

## 🎓 What You'll Learn

### **RabbitMQ UI Shows:**
- Queues and their message counts
- Exchange routing
- Connection status
- Message details

### **Kafka UI Shows:**
- Topics and partitions
- Message history
- Consumer groups
- Message payloads

**Perfect for visual learning!** 🎉

