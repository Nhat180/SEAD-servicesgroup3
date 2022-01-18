package com.example.servicesgroup3.controller;

import com.example.servicesgroup3.model.Category;
import com.example.servicesgroup3.service.CategoryService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryKafkaController {
    static final Logger logger = LoggerFactory.getLogger(CategoryKafkaController.class);
    final String TOPIC = "j71ep171-default";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private Gson gson;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping
    public void createCategory(@RequestBody Category category) {
        String categoryJson = gson.toJson(category);
        logger.info(String.format("#### -> Produce created category -> %s", categoryJson));
        kafkaTemplate.send(TOPIC + "_CATE_CREATE", categoryJson);
    }

    @PutMapping("/{id}")
    public void updateCategory(@PathVariable(value = "id") Long id, @RequestBody Category category) {
        category.setCategoryId(id);
        String categoryJson = gson.toJson(category);
        logger.info(String.format("#### -> Produce updated category -> %s", categoryJson));
        kafkaTemplate.send(TOPIC + "_CATE_UPDATE", categoryJson);
    }

//    @DeleteMapping("/{id}")
//    public void deleteCategory(@PathVariable(value = "id") Long id) {
//        String idStr = id.toString();
//        logger.info(String.format("#### -> Produce deleted category's id -> %s", idStr));
//        kafkaTemplate.send(TOPIC + "_DELETE", idStr);
//    }
}
