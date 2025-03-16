package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dto.ProductDto;
import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(@RequestParam(required = false, name = "categoryId", value = "categoryId") UUID categoryId, @RequestParam(required = false, name = "typeId", value = "typeId") UUID typeId) {
        List<ProductDto> productList = productService.getAllProducts(categoryId, typeId);
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductDto productDto) {
        Product newProduct = productService.addProduct(productDto);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
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
