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

## API docs (Swagger UI)
- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8080/v3/api-docs`

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

## Jaeger tracing
The service exports traces via OTLP to Jaeger.

Run Jaeger all-in-one (enables OTLP gRPC on 4317 and UI on 16686):
```bash
docker run -d --name jaeger \
  -e COLLECTOR_OTLP_ENABLED=true \
  -p 16686:16686 -p 4317:4317 -p 4318:4318 \
  jaegertracing/all-in-one:1.54
```

App settings (`src/main/resources/application.properties`):
- `spring.application.name=order-service`
- `management.tracing.enabled=true`
- `management.tracing.sampling.probability=1.0`
- `management.otlp.tracing.endpoint=http://localhost:4317`

View traces: open `http://localhost:16686`, select service `order-service`, and search. Stop Jaeger with `docker rm -f jaeger`.
