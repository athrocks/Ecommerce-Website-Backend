package com.ecommerce.backend.services;

import com.ecommerce.backend.dto.CategoryDto;
import com.ecommerce.backend.dto.CategoryTypeDto;
import com.ecommerce.backend.entities.Category;
import com.ecommerce.backend.entities.CategoryType;
import com.ecommerce.backend.exceptions.ResourceNotFoundEx;
import com.ecommerce.backend.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategoryById(UUID categoryId) {
         Optional<Category> category = categoryRepository.findById(categoryId);
         return category.orElse(null);
    }

    public Category createCategory(CategoryDto categoryDto) {
        Category category = mapToEntity(categoryDto);
        return categoryRepository.save(category);
    }

    private Category mapToEntity(CategoryDto categoryDto) {
        Category category = Category.builder()
                .code(categoryDto.getCode())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .build();

        if(categoryDto.getCategoryTypeList() != null){
            List<CategoryType> categoryTypes = mapToCategoryTypesList(categoryDto.getCategoryTypeList(),category);
            category.setCategoryTypes(categoryTypes);
        }

        return category;
    }

    private List<CategoryType> mapToCategoryTypesList(List<CategoryTypeDto> categoryTypeList, Category category) {
        return categoryTypeList.stream().map(categoryTypeDto -> {
            CategoryType categoryType = new CategoryType();
            categoryType.setCode(categoryTypeDto.getCode());
            categoryType.setName(categoryTypeDto.getName());
            categoryType.setDescription(categoryTypeDto.getDescription());
            categoryType.setCategory(category);
            return categoryType;
        }).collect(Collectors.toList());
    }

    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }

//    public Category updateCategory(CategoryDto categoryDto, UUID categoryId) {
//        Category category = categoryRepository
//                .findById(categoryId)
//                .orElseThrow(() -> new ResourceNotFoundEx("Category not found with Id " + categoryId));
//
//        if (categoryDto.getName() != null) {
//            category.setName(categoryDto.getName());
//        }
//
//        if (categoryDto.getDescription() != null) {
//            category.setDescription(categoryDto.getDescription());
//        }
//
//        if (categoryDto.getCode() != null) {
//            category.setCode(categoryDto.getCode());
//        }
//
//        List<CategoryType> existingList = category.getCategoryTypes();
//        List<CategoryType> categoryTypesList = new ArrayList<>();
//
//        if (categoryDto.getCategoryTypeList() != null) {
//            categoryDto.getCategoryTypeList().forEach(categoryTypeDto -> {
//                if (null != categoryTypeDto.getId()) {
//                    Optional<CategoryType> categoryType = existingList.stream().filter(t -> t.getId().equals(categoryTypeDto.getId())).findFirst();
//                    CategoryType categoryType1 = categoryType.get();
//                    categoryType1.setCode(categoryTypeDto.getCode());
//                    categoryType1.setName(categoryTypeDto.getName());
//                    categoryType1.setDescription(categoryTypeDto.getDescription());
//                    categoryTypesList.add(categoryType1);
//                } else {
//                    CategoryType categoryType = new CategoryType();
//                    categoryType.setCode(categoryTypeDto.getCode());
//                    categoryType.setName(categoryTypeDto.getName());
//                    categoryType.setDescription(categoryTypeDto.getDescription());
//                    categoryType.setCategory(category);
//                    categoryTypesList.add(categoryType);
//                }
//            });
//        }
//        category.setCategoryTypes(categoryTypesList);
//
////        Category categoryUpdate = categoryRepository.save(category);
//        return categoryRepository.save(category);
//    }

    @Transactional
    public Category updateCategory(CategoryDto categoryDto, UUID categoryId) {
        Category category = categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundEx("Category not found with Id " + categoryId));

        if (categoryDto.getName() != null) {
            category.setName(categoryDto.getName());
        }

        if (categoryDto.getDescription() != null) {
            category.setDescription(categoryDto.getDescription());
        }

        if (categoryDto.getCode() != null) {
            category.setCode(categoryDto.getCode());
        }

        // Updating categoryTypes
        if (categoryDto.getCategoryTypeList() != null) {
            List<CategoryType> existingTypes = category.getCategoryTypes();
            List<CategoryType> updatedCategoryTypes = new ArrayList<>();

            for (CategoryTypeDto categoryTypeDto : categoryDto.getCategoryTypeList()) {
                if (categoryTypeDto.getId() != null) {
                    // Try to find an existing category type
                    Optional<CategoryType> existingTypeOpt = existingTypes.stream()
                            .filter(t -> t.getId().equals(categoryTypeDto.getId()))
                            .findFirst();

                    if (existingTypeOpt.isPresent()) {
                        // Update existing category type
                        CategoryType existingType = existingTypeOpt.get();
                        existingType.setCode(categoryTypeDto.getCode());
                        existingType.setName(categoryTypeDto.getName());
                        existingType.setDescription(categoryTypeDto.getDescription());
                        updatedCategoryTypes.add(existingType);
                    } else {
                        throw new ResourceNotFoundEx("CategoryType not found with ID " + categoryTypeDto.getId());
                    }
                } else {
                    // Create a new category type
                    CategoryType newCategoryType = new CategoryType();
                    newCategoryType.setCode(categoryTypeDto.getCode());
                    newCategoryType.setName(categoryTypeDto.getName());
                    newCategoryType.setDescription(categoryTypeDto.getDescription());
                    newCategoryType.setCategory(category);
                    updatedCategoryTypes.add(newCategoryType);
                }
            }

            // Remove old category types that are not in the updated list
            existingTypes.clear();
            existingTypes.addAll(updatedCategoryTypes);
        }

        return categoryRepository.save(category);
    }


    public void deleteCategory(UUID categoryId) {
        categoryRepository.deleteById(categoryId);
    }
}
