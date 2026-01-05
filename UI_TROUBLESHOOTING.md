# 🔧 UI Troubleshooting Guide

## ✅ RabbitMQ UI - Working!

**URL:** http://localhost:15672  
**Login:** `guest` / `guest`  
**Status:** ✅ **This one works perfectly!**

---

## 📊 Kafka UI - Connection Issue

**URL:** http://localhost:9000  
**Status:** ⏳ May be loading/connecting

### **Why It's Loading:**

Kafdrop (Kafka UI) needs to connect to Kafka. This can take 10-30 seconds.

### **What to Do:**

1. **Wait 15-30 seconds** after opening http://localhost:9000
2. **Refresh the page** (Press F5 or Cmd+R)
3. **You should see:**
   - List of topics
   - `order-events` topic
   - Consumer groups

---

## 🔍 If Kafka UI Still Doesn't Work

### **Check 1: Is Kafka Running?**
```bash
kafka-topics --list --bootstrap-server localhost:9092
```

**Should show:**
```
__consumer_offsets
order-events
```

### **Check 2: Restart Kafdrop**
```bash
docker restart kafdrop
```

Wait 15 seconds, then refresh http://localhost:9000

### **Check 3: View Kafdrop Logs**
```bash
docker logs kafdrop
```

Look for connection errors.

### **Check 4: Recreate Kafdrop**
```bash
docker stop kafdrop
docker rm kafdrop
docker run -d --name kafdrop --network host -e KAFKA_BROKERCONNECT=localhost:9092 obsidiandynamics/kafdrop
```

Wait 15 seconds, then try http://localhost:9000 again.

---

## 🎯 Quick Fix Commands

### **Restart Kafka UI:**
```bash
docker restart kafdrop
```

### **Check Status:**
```bash
docker ps | grep kafdrop
```

### **View Logs:**
```bash
docker logs kafdrop | tail -20
```

---

## ✅ Working Solution

**For now, use RabbitMQ UI which works perfectly:**
- http://localhost:15672
- Login: `guest` / `guest`

**Kafka UI may take time to connect. Be patient and refresh!**

---

## 📊 Current Status

| UI | URL | Status | Notes |
|----|-----|--------|-------|
| **RabbitMQ** | http://localhost:15672 | ✅ **Working** | Login: guest/guest |
| **Kafka** | http://localhost:9000 | ⏳ **Connecting** | Wait 15-30 sec, then refresh |

---

## 💡 Pro Tip

**RabbitMQ UI is fully functional and shows everything you need!**

You can:
- See queues and messages
- View exchanges
- Monitor connections
- Publish test messages

**Use RabbitMQ UI while Kafka UI connects!** 🎉

