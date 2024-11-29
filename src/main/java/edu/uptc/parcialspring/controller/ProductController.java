package edu.uptc.parcialspring.controller;

import edu.uptc.parcialspring.entities.Product;
import edu.uptc.parcialspring.service.ProductService;
import edu.uptc.parcialspring.handling.ResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllProducts() {
        try {
            List<Product> result = productService.getAllProducts();
            return ResponseHandler.generateResponse("Products retrieved successfully", HttpStatus.OK, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        try {
            Optional<Product> result = productService.getProductById(id);
            return result.map(value -> ResponseHandler.generateResponse("Product found", HttpStatus.OK, value))
                    .orElseGet(() -> ResponseHandler.generateResponse("Product not found", HttpStatus.NOT_FOUND, null));
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody Product product) {
        try {
            Product result = productService.saveProduct(product);
            return ResponseHandler.generateResponse("Product created successfully", HttpStatus.CREATED, result);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Optional<Product> result = productService.updateProduct(id, product);
            return result.map(value -> ResponseHandler.generateResponse("Product updated successfully", HttpStatus.OK, value))
                    .orElseGet(() -> ResponseHandler.generateResponse("Product not found", HttpStatus.NOT_FOUND, null));
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        try {
            boolean deleted = productService.deleteProduct(id);
            if (deleted) {
                return ResponseHandler.generateResponse("Product deleted successfully", HttpStatus.NO_CONTENT, null);
            } else {
                return ResponseHandler.generateResponse("Product not found", HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error", HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
