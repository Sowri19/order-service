# 🖥️ How to Access the UIs - Simple Guide

## ✅ You DON'T Need Docker Desktop UI!

**Just open your web browser!** The UIs run in Docker, but you access them through your browser.

---

## 🌐 Access via Web Browser

### **1. RabbitMQ UI**

**Open your web browser and go to:**
```
http://localhost:15672
```

**Or use command:**
```bash
open http://localhost:15672
```

**Login:**
- Username: `guest`
- Password: `guest`

**That's it!** You'll see the RabbitMQ Management Dashboard.

---

### **2. Kafka UI (Kafdrop)**

**Open your web browser and go to:**
```
http://localhost:9000
```

**Or use command:**
```bash
open http://localhost:9000
```

**No login needed!** You'll see the Kafka UI immediately.

---

## 🔍 Check if They're Running

### **Check Kafka UI Container:**
```bash
docker ps | grep kafdrop
```

**If you see kafdrop running, it's working!**

### **Check RabbitMQ:**
```bash
rabbitmqctl status
```

**If you see status, it's working!**

---

## 🧪 Quick Test

### **Step 1: Open RabbitMQ UI**
```bash
open http://localhost:15672
```
- Login: `guest` / `guest`
- You should see the dashboard!

### **Step 2: Open Kafka UI**
```bash
open http://localhost:9000
```
- No login needed
- You should see topics list!

### **Step 3: Create an Order (to see messages)**
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"items":[{"productId":1,"quantity":1}]}'
```

### **Step 4: Refresh Both UIs**
- **RabbitMQ UI:** Go to "Queues" → See `order-queue` with messages
- **Kafka UI:** Click `order-events` topic → See messages

---

## 📊 What You'll See

### **RabbitMQ UI (http://localhost:15672):**
- Dashboard with overview
- **Queues** tab → `order-queue`
- **Exchanges** tab → `order-exchange`
- **Connections** tab → Active connections

### **Kafka UI (http://localhost:9000):**
- List of topics
- Click `order-events` → See messages
- Consumer groups
- Partition details

---

## 🐳 About Docker Images

**You don't need to do anything with Docker Desktop UI!**

The Docker images you see (like `obsidiandynamics/kafdrop`) are just containers running in the background. You access the UIs through your **web browser**, not through Docker Desktop.

**Think of it like this:**
- Docker = The engine running the UI
- Browser = How you see and use the UI

---

## 🎯 Simple Steps

1. **Open your web browser** (Chrome, Safari, Firefox, etc.)
2. **Type the URL:**
   - RabbitMQ: `http://localhost:15672`
   - Kafka: `http://localhost:9000`
3. **That's it!** The UI will load.

**No need to use Docker Desktop UI at all!**

---

## ✅ Quick Access Commands

```bash
# Open RabbitMQ UI
open http://localhost:15672

# Open Kafka UI
open http://localhost:9000
```

**Just copy and paste these in your terminal!**

---

## 🎓 Summary

- ✅ **RabbitMQ UI:** http://localhost:15672 (guest/guest)
- ✅ **Kafka UI:** http://localhost:9000 (no login)
- ✅ **Access via:** Your web browser (not Docker Desktop)
- ✅ **Docker:** Just runs in background (you don't need to interact with it)

**Open your browser and try it now!** 🚀

