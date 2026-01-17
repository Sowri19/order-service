package com.mycompany.service.impl;

import com.mycompany.model.Product;
import com.mycompany.repository.ProductRepository;
import com.mycompany.service.InventoryService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Optional<Product> adjustStock(Long productId, int delta) {
        if (productId == null) return Optional.empty();
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) return Optional.empty();

        Product product = productOpt.get();
        int newStock = product.getStock() + delta;
        if (newStock < 0) return Optional.empty();

        product.setStock(newStock);
        return Optional.of(productRepository.save(product));
    }

    @Override
    public Optional<Product> setStock(Long productId, Integer stock) {
        if (productId == null) return Optional.empty();
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) return Optional.empty();
        if (stock == null || stock < 0) return Optional.empty();

        Product product = productOpt.get();
        product.setStock(stock);
        return Optional.of(productRepository.save(product));
    }
}
