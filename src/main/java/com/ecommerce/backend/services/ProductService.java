package com.ecommerce.backend.services;

import com.ecommerce.backend.dto.ProductDto;
import com.ecommerce.backend.entities.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
//    public List<Product> getAllProducts();
    public List<Product> getAllProducts(UUID categoryId, UUID typeId);
    public Product addProduct(ProductDto product);
}
