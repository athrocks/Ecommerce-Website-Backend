package com.ecommerce.backend.services;

import com.ecommerce.backend.dto.ProductDto;
import com.ecommerce.backend.entities.Product;
import com.ecommerce.backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
//    public List<Product> getAllProducts(UUID categoryId, UUID typeId) {
    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        // To-do mapping of products into productDto
        return products;
    }

    @Override
    public Product addProduct(ProductDto product) {

        return null;
    }

    private Product createProduct(ProductDto productDto) {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setBrand(productDto.getBrand());
        product.setNewArrival(productDto.isNewArrival());

        return product;
    }
}
