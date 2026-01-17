package com.mycompany.dto.request;

import com.mycompany.model.OrderStatus;

/**
 * DTO for updating order status.
 */
public class UpdateStatusRequest {
    private OrderStatus status;

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
