# 📊 Kafka UI Setup Guide

## ✅ RabbitMQ UI - Already Working!

**RabbitMQ Management UI is already running!**
- **URL:** http://localhost:15672
- **Username:** `guest`
- **Password:** `guest`
- **Status:** ✅ Ready to use!

**Try it now:**
```bash
open http://localhost:15672
```

---

## 🐳 Kafka UI Setup (Kafdrop via Docker)

### **Step 1: Start Docker Desktop**

Docker needs to be running to use Kafdrop.

**Option A: Start via Command:**
```bash
open -a Docker
```

**Option B: Start Manually:**
1. Open **Docker Desktop** application
2. Wait for it to start (whale icon in menu bar)
3. When it says "Docker Desktop is running", proceed

### **Step 2: Start Kafdrop (Kafka UI)**

Once Docker is running, execute:

```bash
docker run -d \
  --name kafdrop \
  -p 9000:9000 \
  -e KAFKA_BROKERCONNECT=localhost:9092 \
  obsidiandynamics/kafdrop
```

### **Step 3: Access Kafka UI**

Open in browser:
```bash
open http://localhost:9000
```

Or visit: **http://localhost:9000**

---

## 🎯 What You'll See

### **RabbitMQ UI (http://localhost:15672):**
- **Queues** - See `order-queue`
- **Exchanges** - See `order-exchange`
- **Connections** - Active connections
- **Messages** - View and publish messages

### **Kafka UI (http://localhost:9000):**
- **Topics** - See `order-events`
- **Messages** - View all messages
- **Consumer Groups** - See `notification-service-group`
- **Partitions** - Topic partition details

---

## 🧪 Test Both UIs

### **1. Create an Order:**
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"items":[{"productId":1,"quantity":1}]}'
```

### **2. Check RabbitMQ UI:**
1. Go to http://localhost:15672
2. Login: `guest` / `guest`
3. Click **"Queues"** → See `order-queue`
4. Click on queue → View messages

### **3. Check Kafka UI:**
1. Go to http://localhost:9000
2. Click **"order-events"** topic
3. See messages appear
4. Click message → View details

---

## 🔧 Docker Commands for Kafka UI

### **Start Kafdrop:**
```bash
docker start kafdrop
```

### **Stop Kafdrop:**
```bash
docker stop kafdrop
```

### **View Logs:**
```bash
docker logs kafdrop
```

### **Remove Container:**
```bash
docker rm -f kafdrop
```

### **Restart:**
```bash
docker restart kafdrop
```

---

## 📊 Port Summary

| Service | Port | URL | Login |
|---------|------|-----|-------|
| RabbitMQ UI | 15672 | http://localhost:15672 | guest/guest |
| **Kafka UI (Kafdrop)** | **9000** | **http://localhost:9000** | **None** |

---

## 🎓 Learning with UIs

### **RabbitMQ UI Features:**
- ✅ Visual queue monitoring
- ✅ See message flow
- ✅ Publish test messages
- ✅ Monitor connections
- ✅ View exchange bindings

### **Kafka UI Features:**
- ✅ Browse topics
- ✅ View message history
- ✅ See consumer groups
- ✅ Monitor partitions
- ✅ View message payloads

---

## ✅ Quick Start

1. **Start Docker Desktop** (if not running)
2. **Run Kafdrop:**
   ```bash
   docker run -d --name kafdrop -p 9000:9000 -e KAFKA_BROKERCONNECT=localhost:9092 obsidiandynamics/kafdrop
   ```
3. **Open UIs:**
   - RabbitMQ: http://localhost:15672 (guest/guest)
   - Kafka: http://localhost:9000

**Both UIs are perfect for learning and monitoring!** 🎉

