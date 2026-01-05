# Order Service – Orders, Products, Users with Kafka & RabbitMQ Events

## Overview
Spring Boot 3 / Java 17 service for a simple e-commerce backend (users, products, orders). When an order is created it:
- Persists to PostgreSQL
- Publishes an event to Kafka topic `order-events`
- Publishes an event to RabbitMQ exchange `order-exchange` → routing key `order.created` → queue `order-queue`

Use this repo to demo side-by-side Kafka vs RabbitMQ event publishing plus REST CRUD endpoints.

## Architecture
- **Service:** single Spring Boot app (`src/main/java/com/mycompany/*`) named `order-service`
- **Data:** PostgreSQL (`spring.datasource.*` in `src/main/resources/application.properties`)
- **Events:** Kafka producer (`KafkaProducerService`) + RabbitMQ producer (`RabbitMQProducerService`)
- **HTTP:** Controllers for users/products/orders under `/api`
- **UIs:** RabbitMQ Management UI at `http://localhost:15672` (guest/guest); Kafka UI (Kafdrop) at `http://localhost:9000`

## Prerequisites
- JDK 17+, Maven
- PostgreSQL running locally with db `mydatabase` and user `sowri` (set password in `application.properties` if needed)
- Kafka broker on `localhost:9092` with topic `order-events`
- RabbitMQ on `localhost:5672` with user `guest/guest` (management UI on 15672)

## Run the app
```bash
mvn spring-boot:run
```
The app starts on `http://localhost:8080`.

## Example API calls
- List products: `curl http://localhost:8080/api/products`
- Create order (fires Kafka + RabbitMQ events):
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{
        "userId":1,
        "items":[{"productId":1,"quantity":1}]
      }'
```

## Kafka UI (Kafdrop)
Start and open Kafdrop (expects broker on localhost:9092):
```bash
docker run -d --name kafdrop -p 9000:9000 \
  --add-host=localhost:host-gateway \
  -e KAFKA_BROKERCONNECT=localhost:9092 \
  obsidiandynamics/kafdrop
# then open http://localhost:9000
```

## RabbitMQ UI
Open `http://localhost:15672` (user `guest`, password `guest`).

## Event flow quick check
- Kafka CLI: `kafka-console-consumer --bootstrap-server localhost:9092 --topic order-events --from-beginning`
- RabbitMQ UI: open queue `order-queue` and view messages.

## Clean workspace
- Build artifacts under `target/` are ignored via `.gitignore`.

## Publish to GitHub
```bash
git init
git add .
git commit -m "Initial commit with Kafka/RabbitMQ order events"
git branch -M main
git remote add origin <your-github-repo-url>
git push -u origin main
```
Replace `<your-github-repo-url>` with your repo and push so others can clone and run.
