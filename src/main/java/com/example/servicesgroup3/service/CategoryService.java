package com.example.servicesgroup3.service;

import com.example.servicesgroup3.model.Category;
import com.example.servicesgroup3.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class CategoryService {
    final String CATEGORY_CACHE = "Category";

    @Autowired
    private CategoryRepository categoryRepository;

    public void addCategory (Category category) {
        this.categoryRepository.save(category);
    }

    @Cacheable(value = CATEGORY_CACHE, key = "#id")
    public Category getCategory (Long id) {
        Category category = new Category();
        try {
            category = this.categoryRepository.findById(id)
                    .orElseThrow(() -> new Exception("Category not found:: " + id));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return category;
    }

    @Cacheable(value = CATEGORY_CACHE)
    public List<Category> getAllCategory() {
        return this.categoryRepository.findAll();
    }

    @Cacheable(value = CATEGORY_CACHE, key = "{#page, #size}")
    public Map<String, Object> getAllCategoryByPage(int page, int size) {
        Map<String, Object> res = new HashMap<>();
        try {
            List<Category> categories = new ArrayList<>();
            Pageable paging = PageRequest.of(page,size);

            Page<Category> categoryPage;
            categoryPage = categoryRepository.findAll(paging);


            categories = categoryPage.getContent(); // Assign paging content to list and then return to UI

            res.put("categories", categories);
            res.put("currentPage", categoryPage.getNumber());
            res.put("totalCategories", categoryPage.getTotalElements());
            res.put("totalPages", categoryPage.getTotalPages());

            return res;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateCategory (Long id, Category category) {
        Category category1 = new Category();
        category1.setCategoryId(id);
        category1.setType(category.getType());
        category1.setUrlImg(category.getUrlImg());
        categoryRepository.save(category1);
    }

    public void deleteCategory (Long id) {
        Category category = new Category();
        try {
            category = this.categoryRepository.findById(id)
                    .orElseThrow(() -> new Exception("Category not found:: " + id));
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.categoryRepository.delete(category);
    }

}
