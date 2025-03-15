package com.ecommerce.backend.services;

import com.ecommerce.backend.dto.ProductDto;
import com.ecommerce.backend.dto.ProductResourceDto;
import com.ecommerce.backend.dto.ProductVariantDto;
import com.ecommerce.backend.entities.*;
import com.ecommerce.backend.repositories.ProductRepository;
import com.ecommerce.backend.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryService categoryService;

    @Override
    public List<Product> getAllProducts(UUID categoryId, UUID typeId) {

        Specification<Product> productSpecification = Specification.where(null);

        if (categoryId != null) {
            productSpecification = productSpecification.and(ProductSpecification.hasCategoryId(categoryId));
        }
        if (typeId != null) {
            productSpecification = productSpecification.and(ProductSpecification.hasCategoryTypeId(typeId));
        }

        List<Product> products = productRepository.findAll(productSpecification);
//        To-do mapping of products into productDto
        return products;
    }

    @Override
    public Product addProduct(ProductDto productDto) {
        Product product = mapToProductEntity(productDto);
        return productRepository.save(product);
    }

    private Product mapToProductEntity(ProductDto productDto) {
        Product product = new Product();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setBrand(productDto.getBrand());
        product.setNewArrival(productDto.isNewArrival());
        product.setRating(productDto.getRating());

        Category category = categoryService.getCategoryById(productDto.getCategoryId());
        if (category != null) {
            product.setCategory(category); // mapped category

            UUID categoryTypeId = productDto.getCategoryTypeId();

            CategoryType categoryType =
                    category.getCategoryTypes()
                            .stream()
                            .filter(categoryType1 -> categoryType1.getId().equals(categoryTypeId))
                            .findFirst()
                            .orElse(null);

            product.setCategoryType(categoryType); // mapped categoryType
        }

        if (productDto.getVariants() != null) {
            product.setProductVariants(mapToProductVariant(productDto.getVariants(), product));
        }

        if (productDto.getProductResources() != null) {
            product.setResources(mapToProductResources(productDto.getProductResources(), product));
        }

        return product;
    }

    private List<ProductVariant> mapToProductVariant(List<ProductVariantDto> productVariantDtos, Product product) {
        return productVariantDtos
                .stream()
                .map((productVariantDto) -> {
                    ProductVariant productVariant = new ProductVariant();
                    productVariant.setColor(productVariantDto.getColor());
                    productVariant.setSize(productVariantDto.getSize());
                    productVariant.setStockQuantity(productVariantDto.getStockQuantity());
                    productVariant.setProduct(product);
                    return productVariant;
                }).collect(Collectors.toList());
    }

    private List<Resources> mapToProductResources(List<ProductResourceDto> productResources, Product product) {

        return productResources
                .stream()
                .map(productResourceDto -> {
                    Resources resources = new Resources();
                    resources.setName(productResourceDto.getName());
                    resources.setType(productResourceDto.getType());
                    resources.setUrl(productResourceDto.getUrl());
                    resources.setIsPrimary(productResourceDto.getIsPrimary());
                    resources.setProduct(product);
                    return resources;
                }).collect(Collectors.toList());
    }
}
