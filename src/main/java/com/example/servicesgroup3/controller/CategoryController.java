package com.example.servicesgroup3.controller;

import com.example.servicesgroup3.model.Category;
import com.example.servicesgroup3.model.Services;
import com.example.servicesgroup3.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    @PostMapping
//    public void createCategory(@RequestBody Category category) {
//        categoryService.addCategory(category);
//    }

    @GetMapping
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    @GetMapping("/{id}")
    public Category getCategory (@PathVariable(value = "id") Long id) {
        return categoryService.getCategory(id);
    }

    @GetMapping("/pagination")
    public ResponseEntity<Map<String, Object>> getAllServicesByPage (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return new ResponseEntity<>(categoryService.getAllCategoryByPage(page,size), HttpStatus.OK);
    }

//    @PutMapping("/{id}")
//    public void updateCategory(@PathVariable(value = "id") Long id, @RequestBody Category category) {
//        categoryService.updateCategory(id,category);
//    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable(value = "id") Long id) {
        categoryService.deleteCategory(id);
    }

    @PostMapping("/many")
    public void createManyServices(@RequestBody Category[] categoryArray) {
        for (Category category : categoryArray) {
            categoryService.addCategory(category);
        }
    }

}
