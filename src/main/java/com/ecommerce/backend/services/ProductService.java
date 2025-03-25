package com.ecommerce.backend.services;

import com.ecommerce.backend.dto.ProductDto;
import com.ecommerce.backend.entities.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    //    public List<Product> getAllProducts();
    public List<ProductDto> getAllProducts(UUID categoryId, UUID typeId);

    public Product addProduct(ProductDto product);

    public ProductDto getProductBySlug(String slug);

    public ProductDto getProductById(UUID id);

    public Product updateProduct(ProductDto productDto);

    public void deleteCategory(UUID productId);
}
