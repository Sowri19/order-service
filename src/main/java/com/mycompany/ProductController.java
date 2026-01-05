package com.mycompany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

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

    // Dependency Injection: Spring automatically provides ProductRepository instance
    @Autowired
    private ProductRepository productRepository;

    /**
     * GET /api/products
     * Returns all products in the database
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * GET /api/products/{id}
     * Returns a specific product by ID
     * Returns 404 if product not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Optional<Product> product = productRepository.findById(id);
        
        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * POST /api/products
     * Creates a new product
     * Request body should contain: name, description, price, stock
     */
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product savedProduct = productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    /**
     * PUT /api/products/{id}
     * Updates an existing product
     * Returns 404 if product not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setName(productDetails.getName());
            product.setDescription(productDetails.getDescription());
            product.setPrice(productDetails.getPrice());
            product.setStock(productDetails.getStock());
            
            Product updatedProduct = productRepository.save(product);
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE /api/products/{id}
     * Deletes a product by ID
     * Returns 404 if product not found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

