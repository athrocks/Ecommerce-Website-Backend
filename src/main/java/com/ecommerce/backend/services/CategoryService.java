package com.ecommerce.backend.services;

import com.ecommerce.backend.dto.CategoryDto;
import com.ecommerce.backend.dto.CategoryTypeDto;
import com.ecommerce.backend.entities.Category;
import com.ecommerce.backend.entities.CategoryType;
import com.ecommerce.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
