package com.ecommerce.backend.controllers;

import com.ecommerce.backend.dto.CategoryDto;
import com.ecommerce.backend.entities.Category;
import com.ecommerce.backend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable(value = "id",required = true) UUID categoryId) {
        Category category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto categoryDto) {
        Category category = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(category, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categoryList = categoryService.getAllCategory();
        return new ResponseEntity<>(categoryList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryDto categoryDto, @PathVariable(value = "id",required = true) UUID categoryId){
        Category updatedCategory = categoryService.updateCategory(categoryDto,categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(value = "id",required = true) UUID categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok().build();
    }
}
