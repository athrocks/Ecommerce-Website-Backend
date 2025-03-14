package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dto.ProductDto;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @GetMapping
    public List<ProductDto> getProducts() {
        return Collections.EMPTY_LIST;
    }

    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto product) {
        return null;
    }

    @GetMapping("/{productId}")
    public ProductDto getProductById(@PathVariable int productId) {
        return null;
    }

    @PutMapping("/{productId}")
    public void updateProduct() {

    }

    @DeleteMapping("/{productId}")
    public void deleteProduct() {

    }
}
