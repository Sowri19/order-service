package com.mycompany.controller;

import com.mycompany.dto.request.InventoryAdjustRequest;
import com.mycompany.dto.request.InventorySetRequest;
import com.mycompany.dto.response.ProductResponse;
import com.mycompany.model.Product;
import com.mycompany.service.InventoryService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @PostMapping("/adjust")
    public ResponseEntity<ProductResponse> adjust(@RequestBody InventoryAdjustRequest request) {
        Optional<Product> product = inventoryService.adjustStock(request.getProductId(), request.getDelta());
        return product.map(p -> ResponseEntity.ok(toProductResponse(p)))
            .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> set(@PathVariable Long productId, @RequestBody InventorySetRequest request) {
        Optional<Product> product = inventoryService.setStock(productId, request.getStock());
        return product.map(p -> ResponseEntity.ok(toProductResponse(p)))
            .orElseGet(() -> ResponseEntity.badRequest().build());
    }

    private ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStock()
        );
    }
}
