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
        List<Product> products = productService.getAllProducts();
        return ResponseHandler.generateResponse("Products retrieved successfully", HttpStatus.OK, products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable Long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(value -> ResponseHandler.generateResponse("Product found", HttpStatus.OK, value))
                .orElseGet(() -> ResponseHandler.generateResponse("Product not found", HttpStatus.NOT_FOUND, null));
    }

    @PostMapping
    public ResponseEntity<Object> saveProduct(@RequestBody Product product) {
        Product savedProduct = productService.saveProduct(product);
        return ResponseHandler.generateResponse("Product created successfully", HttpStatus.CREATED, savedProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> updatedProduct = productService.updateProduct(id, product);
        return updatedProduct.map(value -> ResponseHandler.generateResponse("Product updated successfully", HttpStatus.OK, value))
                .orElseGet(() -> ResponseHandler.generateResponse("Product not found", HttpStatus.NOT_FOUND, null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        if (deleted) {
            return ResponseHandler.generateResponse("Product deleted successfully", HttpStatus.NO_CONTENT, null);
        } else {
            return ResponseHandler.generateResponse("Product not found", HttpStatus.NOT_FOUND, null);
        }
    }
}
