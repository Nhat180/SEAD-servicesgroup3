package com.example.servicesgroup3.engine;

import com.example.servicesgroup3.model.Category;
import com.example.servicesgroup3.service.CategoryService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class CategoryConsumer {
    final Logger logger = LoggerFactory.getLogger(CategoryConsumer.class);
    final String TOPIC = "j71ep171-default";
    final String GROUP_ID = "CATEGORY_ID";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private Gson gson;

    @KafkaListener(topics = TOPIC + "_CATE_CREATE", groupId = GROUP_ID)
    public void createCategory(String categoryJson) {
        Category category = gson.fromJson(categoryJson, Category.class);
        logger.info(String.format("#### -> Consume created category -> %s", categoryJson));
        categoryService.addCategory(category);
    }

    @KafkaListener(topics = TOPIC + "_CATE_UPDATE", groupId = GROUP_ID)
    public void updateCategory(String categoryJson) {
        Category category = gson.fromJson(categoryJson, Category.class);
        logger.info(String.format("#### -> Consume updated category -> %s", categoryJson));
        categoryService.updateCategory(category.getCategoryId(), category);
    }

//    @KafkaListener(topics = TOPIC + "_DELETE", groupId = GROUP_ID)
//    public void deleteServices(String idStr) {
//        Long id = Long.parseLong(idStr);
//        logger.info(String.format("#### -> Consume deleted category's id -> %d", id));
//        categoryService.deleteCategory(id);
//    }
}
