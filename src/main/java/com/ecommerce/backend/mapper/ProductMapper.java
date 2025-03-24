package com.ecommerce.backend.mapper;

import com.ecommerce.backend.dto.ProductDto;
import com.ecommerce.backend.dto.ProductResourceDto;
import com.ecommerce.backend.dto.ProductVariantDto;
import com.ecommerce.backend.entities.*;
import com.ecommerce.backend.services.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

//@Component
//@Slf4j
//public class ProductMapper {
//
//    @Autowired
//    private CategoryService categoryService;
//
//    public Product mapToProductEntity(ProductDto productDto) {
//        Product product = new Product();
//
//        product.setName(productDto.getName());
//        product.setDescription(productDto.getDescription());
//        product.setPrice(productDto.getPrice());
//        product.setBrand(productDto.getBrand());
//        product.setNewArrival(productDto.isNewArrival());
//        product.setRating(productDto.getRating());
//
//        Category category = categoryService.getCategoryById(productDto.getCategoryId());
//
//        if (category == null) {
//            log.warn("Category not found for ID: {}", productDto.getCategoryId());
//            return null; // or handle accordingly
//        }
//
//        if (category != null) {
//
//            log.info("Category: {}", category.toString());
//
//            product.setCategory(category); // mapped category
//
//            UUID categoryTypeId = productDto.getCategoryTypeId();
//
//            log.info("CategoryTypeId: {}", categoryTypeId != null ? categoryTypeId.toString() : "NULL");
//
//            CategoryType categoryType =
//                    category.getCategoryTypes()
//                            .stream()
//                            .filter(categoryType1 -> categoryType1.getId().equals(categoryTypeId))
//                            .findFirst()
//                            .orElse(null);
//
//            product.setCategoryType(categoryType); // mapped categoryType
//        }
//
//        if (productDto.getVariants() != null) {
//            product.setProductVariants(mapToProductVariant(productDto.getVariants(), product));
//        }
//
//        if (productDto.getProductResources() != null) {
//            product.setResources(mapToProductResources(productDto.getProductResources(), product));
//        }
//
//        return product;
//    }
//
//    private List<ProductVariant> mapToProductVariant(List<ProductVariantDto> productVariantDtos, Product product) {
//        return productVariantDtos
//                .stream()
//                .map((productVariantDto) -> {
//                    ProductVariant productVariant = new ProductVariant();
//                    productVariant.setColor(productVariantDto.getColor());
//                    productVariant.setSize(productVariantDto.getSize());
//                    productVariant.setStockQuantity(productVariantDto.getStockQuantity());
//                    productVariant.setProduct(product);
//                    return productVariant;
//                }).collect(Collectors.toList());
//    }
//
//    private List<Resources> mapToProductResources(List<ProductResourceDto> productResources, Product product) {
//
//        return productResources
//                .stream()
//                .map(productResourceDto -> {
//                    Resources resources = new Resources();
//                    resources.setName(productResourceDto.getName());
//                    resources.setType(productResourceDto.getType());
//                    resources.setUrl(productResourceDto.getUrl());
//                    resources.setIsPrimary(productResourceDto.getIsPrimary());
//                    resources.setProduct(product);
//                    return resources;
//                }).collect(Collectors.toList());
//    }
//
//    public List<ProductDto> getProductDtos(List<Product> products) {
//        return products.stream().map(this::mapProductToDto).toList();
//    }
//
//    private ProductDto mapProductToDto(Product product) {
//        return ProductDto.builder()
//                .id(product.getId())
//                .brand(product.getBrand())
//                .name(product.getName())
//                .price(product.getPrice())
//                .isNewArrival(product.isNewArrival())
//                .rating(product.getRating())
//                .description(product.getDescription())
//                .thumbnail(getProductThumbnail(product.getResources()))
//                .build();
//    }
//
//    private String getProductThumbnail(List<Resources> resources) {
//        return resources.stream().filter(Resources::getIsPrimary).findFirst().orElse(null).getUrl();
//    }
//
//
//}

@Component
@Slf4j
public class ProductMapper {
    @Autowired
    private CategoryService categoryService;

    public Product mapToProductEntity(ProductDto productDto) {
        Product product = new Product();

        if (productDto.getId() != null) {
            throw new IllegalArgumentException("New product should not have an ID");
        }

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setBrand(productDto.getBrand());
        product.setNewArrival(productDto.isNewArrival());
        product.setPrice(productDto.getPrice());
        product.setRating(productDto.getRating());
        product.setSlug(productDto.getSlug());

        Category category = categoryService.getCategoryById(productDto.getCategoryId());
        if (category == null) {
            throw new IllegalArgumentException("Invalid category ID: " + productDto.getCategoryId());
        }

        product.setCategory(category);

        UUID categoryTypeId = productDto.getCategoryTypeId();

        CategoryType categoryType = category.getCategoryTypes()
                .stream()
                .filter(
                        categoryType1 -> categoryType1.getId().equals(categoryTypeId)
                )
                .findFirst()
                .orElse(null);

        if (categoryType == null) {
            throw new RuntimeException("Category type not found");
        }

        product.setCategoryType(categoryType);

        if (productDto.getVariants() != null) {
            product.setProductVariants(mapToProductVariant(productDto.getVariants(), product));
        } else {
            product.setProductVariants(Collections.emptyList());
        }

        if (productDto.getProductResources() != null) {
            product.setResources(mapToProductResources(productDto.getProductResources(), product));
        } else {
            product.setResources(Collections.emptyList());
        }


        return product;
    }

    private List<Resources> mapToProductResources(List<ProductResourceDto> productResources, Product product) {

        return productResources.stream().map(productResourceDto -> {
            Resources resources = new Resources();
            if (null != productResourceDto.getId()) {
                resources.setId(productResourceDto.getId());
            }
            resources.setName(productResourceDto.getName());
            resources.setType(productResourceDto.getType());
            resources.setUrl(productResourceDto.getUrl());
            resources.setIsPrimary(productResourceDto.getIsPrimary());
            resources.setProduct(product);

            return resources;
        }).collect(Collectors.toList());
    }

    private List<ProductVariant> mapToProductVariant(List<ProductVariantDto> productVariantDtos, Product product) {
        return productVariantDtos.stream().map(productVariantDto -> {
            ProductVariant productVariant = new ProductVariant();

            if (null != productVariantDto.getId()) {
                productVariant.setId(productVariantDto.getId());
            }

            productVariant.setColor(productVariantDto.getColor());
            productVariant.setSize(productVariantDto.getSize());
            productVariant.setStockQuantity(productVariantDto.getStockQuantity());
            productVariant.setProduct(product);

            return productVariant;
        }).collect(Collectors.toList());
    }

    public List<ProductDto> getProductDtos(List<Product> products) {
        return products.stream().map(this::mapProductToDto).toList();
    }

    public ProductDto mapProductToDto(Product product) {

        return ProductDto.builder()
                .id(product.getId())
                .brand(product.getBrand())
                .name(product.getName())
                .price(product.getPrice())
                .isNewArrival(product.isNewArrival())
                .rating(product.getRating())
                .description(product.getDescription())
                .slug(product.getSlug())
                .thumbnail(getProductThumbnail(product.getResources()))
                .build();
    }

    private String getProductThumbnail(List<Resources> resources) {
        return resources.stream()
                .filter(Resources::getIsPrimary)
                .findFirst()
                .orElse(null)
                .getUrl();
    }

    public List<ProductVariantDto> mapProductVariantListToDto(List<ProductVariant> productVariants) {
        return productVariants.stream().map(this::mapProductVariantDto).toList();
    }

    private ProductVariantDto mapProductVariantDto(ProductVariant productVariant) {
        return ProductVariantDto.builder()
                .color(productVariant.getColor())
                .id(productVariant.getId())
                .size(productVariant.getSize())
                .stockQuantity(productVariant.getStockQuantity())
                .build();
    }

    public List<ProductResourceDto> mapProductResourcesListDto(List<Resources> resources) {
        return resources.stream().map(this::mapResourceToDto).toList();
    }

    private ProductResourceDto mapResourceToDto(Resources resources) {
        return ProductResourceDto.builder()
                .id(resources.getId())
                .url(resources.getUrl())
                .name(resources.getName())
                .isPrimary(resources.getIsPrimary())
                .type(resources.getType())
                .build();
    }
}