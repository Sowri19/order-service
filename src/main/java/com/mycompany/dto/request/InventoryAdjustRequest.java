package com.mycompany.dto.request;

public class InventoryAdjustRequest {
    private Long productId;
    private Integer delta;

    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getDelta() {
        return delta;
    }
    public void setDelta(Integer delta) {
        this.delta = delta;
    }
}
