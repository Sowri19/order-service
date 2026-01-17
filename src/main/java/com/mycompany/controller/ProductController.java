package com.mycompany.controller;

import com.mycompany.dto.response.ProductResponse;
import com.mycompany.model.Product;
import com.mycompany.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

/**
 * ProductController - REST API endpoints for Product operations
 * 
 * This controller handles HTTP requests for product management:
 * - GET /api/products - Get all products
 * - GET /api/products/{id} - Get a specific product
 * - POST /api/products - Create a new product
 * - PUT /api/products/{id} - Update a product
 * - DELETE /api/products/{id} - Delete a product
 * 
 * @RestController combines @Controller and @ResponseBody
 * @RequestMapping sets the base URL path for all endpoints in this controller
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    // Dependency Injection: Spring automatically provides ProductService instance
    @Autowired
    private ProductService productService;

    /**
     * GET /api/products
     * Returns all products in the database
     */
    @GetMapping
    @Cacheable("products")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts().stream()
            .map(this::toProductResponse)
            .toList();
    }

    /**
     * GET /api/products/{id}
     * Returns a specific product by ID
     * Returns 404 if product not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        
        return product.map(value -> ResponseEntity.ok(toProductResponse(value)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * POST /api/products
     * Creates a new product
     * Request body should contain: name, description, price, stock
     */
    @PostMapping
    @CacheEvict(value = "products", allEntries = true)
    public ResponseEntity<ProductResponse> createProduct(@RequestBody Product product) {
        Product savedProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(toProductResponse(savedProduct));
    }

    /**
     * PUT /api/products/{id}
     * Updates an existing product
     * Returns 404 if product not found
     */
    @PutMapping("/{id}")
    @CacheEvict(value = "products", allEntries = true)
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Optional<Product> updatedProduct = productService.updateProduct(id, productDetails);
        return updatedProduct.map(value -> ResponseEntity.ok(toProductResponse(value)))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/products/{id}
     * Deletes a product by ID
     * Returns 404 if product not found
     */
    @DeleteMapping("/{id}")
    @CacheEvict(value = "products", allEntries = true)
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
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
