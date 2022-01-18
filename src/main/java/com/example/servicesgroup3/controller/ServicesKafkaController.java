package com.example.servicesgroup3.controller;

import com.example.servicesgroup3.model.Services;
import com.example.servicesgroup3.service.ServicesImpl;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka/service")
public class ServicesKafkaController {
    static final Logger logger = LoggerFactory.getLogger(ServicesKafkaController.class);
    final String TOPIC = "SERVICES";

    @Autowired
    private ServicesImpl servicesImpl;

    @Autowired
    private Gson gson;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @PostMapping
    public void createService(@RequestBody Services services) {
        String servicesJson = gson.toJson(services);
        logger.info(String.format("#### -> Produce created service -> %s", servicesJson));
        kafkaTemplate.send(TOPIC + "_CREATE", servicesJson);
    }

    @PutMapping("/{id}")
    public void updateService(@PathVariable(value = "id") Long id, @RequestBody Services services) {
        services.setServiceId(id);
        String servicesJson = gson.toJson(services);
        logger.info(String.format("#### -> Produce updated service ->%s", servicesJson));
        kafkaTemplate.send(TOPIC + "_UPDATE", servicesJson);
    }

//    @DeleteMapping("/{id}")
//    public void deleteService(@PathVariable(value = "id") Long id) {
//        String idStr = id.toString();
//        logger.info(String.format("#### -> Produce deleted service's id -> %s", idStr));
//        kafkaTemplate.send(TOPIC + "_DELETE", idStr);
//    }
}
