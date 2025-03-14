package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dto.ProductDto;
import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto) {
        Product newProduct = productService.addProduct(productDto);
        return null;
    }

//    @GetMapping("/{productId}")
//    public ProductDto getProductById(@PathVariable int productId) {
//        return null;
//    }
//
//    @PutMapping("/{productId}")
//    public void updateProduct() {
//
//    }
//
//    @DeleteMapping("/{productId}")
//    public void deleteProduct() {
//
//    }
}
