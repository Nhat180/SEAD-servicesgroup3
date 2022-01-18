package com.example.servicesgroup3.engine;

import com.example.servicesgroup3.model.Services;
import com.example.servicesgroup3.service.ServicesImpl;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ServicesConsumer {
    final Logger logger = LoggerFactory.getLogger(ServicesConsumer.class);
    final String TOPIC = "SERVICES";
    final String GROUP_ID = "CATEGORY_ID";

    @Autowired
    private ServicesImpl servicesImpl;

    @Autowired
    private Gson gson;

    @KafkaListener(topics = TOPIC + "_CREATE", groupId = GROUP_ID)
    public void createServices(String servicesJson) {
        Services services = gson.fromJson(servicesJson, Services.class);
        logger.info(String.format("#### -> Consume created service -> %s", servicesJson));
        servicesImpl.createService(services);
    }

    @KafkaListener(topics = TOPIC + "_UPDATE", groupId = GROUP_ID)
    public void updateServices(String servicesJson) {
        Services services = gson.fromJson(servicesJson, Services.class);
        logger.info(String.format("#### -> Consume updated service -> %s", servicesJson));
        servicesImpl.updateService(services.getServiceId(), services);
    }

//    @KafkaListener(topics = TOPIC + "_DELETE", groupId = GROUP_ID)
//    public void deleteServices(String idStr) {
//        Long id = Long.parseLong(idStr);
//        logger.info(String.format("#### -> Consume deleted service's id -> %d", id));
//        servicesImpl.deleteServices(id);
//    }
}
