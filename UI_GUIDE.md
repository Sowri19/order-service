# 🖥️ Message Broker UIs - Visual Monitoring

## ✅ What's Installed

### **RabbitMQ Management UI**
- **URL:** http://localhost:15672
- **Username:** `guest`
- **Password:** `guest`
- **Status:** ✅ Already running!

### **Kafka UI (Kafdrop)**
- **URL:** http://localhost:9000
- **Status:** ✅ Starting via Docker

---

## 🐰 RabbitMQ Management UI

### **Access:**
```bash
# Open in browser
open http://localhost:15672

# Or visit manually:
# http://localhost:15672
```

### **Login:**
- Username: `guest`
- Password: `guest`

### **What You Can See:**
1. **Overview** - System stats, connections, channels
2. **Connections** - Active connections to RabbitMQ
3. **Channels** - Message channels
4. **Exchanges** - See `order-exchange`
5. **Queues** - See `order-queue` with message count
6. **Messages** - View and publish messages
7. **Admin** - User management, virtual hosts

### **Key Features:**
- ✅ See all queues and exchanges
- ✅ View message count in queues
- ✅ Publish test messages
- ✅ Monitor connections
- ✅ View message details

---

## 📊 Kafka UI (Kafdrop)

### **Access:**
```bash
# Open in browser
open http://localhost:9000

# Or visit manually:
# http://localhost:9000
```

### **What You Can See:**
1. **Topics** - See `order-events` topic
2. **Messages** - View messages in topics
3. **Consumers** - See consumer groups
4. **Brokers** - Kafka broker information
5. **Message Details** - View message content, headers, timestamps

### **Key Features:**
- ✅ Browse all Kafka topics
- ✅ View messages in real-time
- ✅ See consumer groups
- ✅ View message payloads
- ✅ Monitor topic partitions

---

## 🧪 Testing with UIs

### **1. Create an Order:**
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"items":[{"productId":1,"quantity":1}]}'
```

### **2. Check RabbitMQ UI:**
1. Go to http://localhost:15672
2. Login: guest / guest
3. Click **"Queues"** tab
4. See `order-queue` with message count
5. Click on queue to see messages

### **3. Check Kafka UI (Kafdrop):**
1. Go to http://localhost:9000
2. Click on **"order-events"** topic
3. See messages in the topic
4. Click on a message to view details

---

## 🔧 Managing Kafka UI

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

### **Restart:**
```bash
docker restart kafdrop
```

---

## 📊 Comparison: RabbitMQ UI vs Kafka UI

| Feature | RabbitMQ UI | Kafka UI (Kafdrop) |
|---------|-------------|-------------------|
| **URL** | http://localhost:15672 | http://localhost:9000 |
| **Login** | guest/guest | No login needed |
| **See Queues/Topics** | ✅ Queues | ✅ Topics |
| **View Messages** | ✅ Yes | ✅ Yes |
| **Publish Messages** | ✅ Yes | ❌ No |
| **Consumer Groups** | ❌ No | ✅ Yes |
| **Partitions** | ❌ No | ✅ Yes |

---

## 🎯 Quick Access

### **RabbitMQ:**
```bash
open http://localhost:15672
# Login: guest / guest
```

### **Kafka:**
```bash
open http://localhost:9000
# No login needed
```

---

## 🎓 Learning Tips

### **RabbitMQ UI:**
1. Go to **Queues** → See `order-queue`
2. Click on queue → See message details
3. Go to **Exchanges** → See `order-exchange`
4. Go to **Connections** → See active connections

### **Kafka UI:**
1. See **Topics** → Click `order-events`
2. View **Messages** → See all messages
3. Check **Consumer Groups** → See `notification-service-group`
4. View **Partitions** → See partition details

---

## ✅ Both UIs Ready!

- ✅ **RabbitMQ UI** - http://localhost:15672 (guest/guest)
- ✅ **Kafka UI** - http://localhost:9000

**Perfect for visual learning and monitoring!** 🎉

