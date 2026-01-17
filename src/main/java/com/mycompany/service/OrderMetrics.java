package com.mycompany.service;

import org.springframework.stereotype.Component;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

/**
 * OrderMetrics - Custom counters for observability dashboards.
 */
@Component
public class OrderMetrics {

    private final Counter ordersCreated;

    public OrderMetrics(MeterRegistry registry) {
        this.ordersCreated = Counter.builder("order_service_orders_created_total")
            .description("Number of orders created via API")
            .register(registry);
    }

    public void incrementOrdersCreated() {
        ordersCreated.increment();
    }
}
