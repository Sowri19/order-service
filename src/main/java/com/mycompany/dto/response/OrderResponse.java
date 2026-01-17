package com.mycompany.dto.response;

import com.mycompany.model.OrderStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for returning order data.
 */
public class OrderResponse {
    private Long id;
    private Long userId;
    private BigDecimal total;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private List<OrderItemResponse> items;

    public OrderResponse(
        Long id,
        Long userId,
        BigDecimal total,
        OrderStatus status,
        LocalDateTime orderDate,
        List<OrderItemResponse> items
    ) {
        this.id = id;
        this.userId = userId;
        this.total = total;
        this.status = status;
        this.orderDate = orderDate;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }
}
