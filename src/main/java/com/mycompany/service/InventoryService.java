package com.mycompany.service;

import com.mycompany.model.Product;
import java.util.Optional;

public interface InventoryService {
    Optional<Product> adjustStock(Long productId, int delta);
    Optional<Product> setStock(Long productId, Integer stock);
}
