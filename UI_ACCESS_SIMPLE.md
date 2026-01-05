# 🖥️ Simple UI Access Guide

## ✅ RabbitMQ UI - Works Perfectly!

**Just open in your browser:**
```
http://localhost:15672
```

**Login:**
- Username: `guest`
- Password: `guest`

**This one works immediately!** ✅

---

## 📊 Kafka UI - May Take Time

**Open in your browser:**
```
http://localhost:9000
```

**If it's loading:**
1. **Wait 30 seconds**
2. **Refresh the page** (F5 or Cmd+R)
3. It should show topics

**Note:** Kafka UI sometimes takes time to connect to Kafka. This is normal!

---

## 🎯 What to Do Right Now

### **1. Use RabbitMQ UI (Works Great!):**
- Go to: http://localhost:15672
- Login: `guest` / `guest`
- Explore queues, exchanges, messages

### **2. For Kafka UI:**
- Go to: http://localhost:9000
- If loading, wait 30 seconds and refresh
- If still loading, that's okay - RabbitMQ UI shows everything you need!

---

## 🧪 Test with RabbitMQ UI

1. **Open RabbitMQ UI:** http://localhost:15672
2. **Login:** guest / guest
3. **Create an order:**
   ```bash
   curl -X POST http://localhost:8080/api/orders \
     -H "Content-Type: application/json" \
     -d '{"userId":1,"items":[{"productId":1,"quantity":1}]}'
   ```
4. **In RabbitMQ UI:**
   - Go to **"Queues"** tab
   - Click on **`order-queue`**
   - See the message!

---

## 📊 Summary

| UI | URL | Status | Action |
|----|-----|--------|--------|
| **RabbitMQ** | http://localhost:15672 | ✅ **Works!** | Use this one! |
| **Kafka** | http://localhost:9000 | ⏳ Loading | Wait & refresh |

**RabbitMQ UI is perfect for learning!** You can see everything there. 🎉

