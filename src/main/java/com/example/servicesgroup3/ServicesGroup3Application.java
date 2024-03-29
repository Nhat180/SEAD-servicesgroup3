package com.example.servicesgroup3;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;


@SpringBootApplication(exclude = {
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class,
		org.springframework.boot.actuate.autoconfigure.security.servlet.ManagementWebSecurityAutoConfiguration.class}
)
@EnableCaching
public class ServicesGroup3Application {
	final String TOPIC = "j71ep171-default";

	public static void main(String[] args) {
		SpringApplication.run(ServicesGroup3Application.class, args);
	}

//	@Bean
//	NewTopic createCateTopic() {
//		return TopicBuilder.name(TOPIC + "_CATE_CREATE").partitions(1).replicas(3).build();
//	}
//	@Bean
//	NewTopic updateCateTopic() {
//		return TopicBuilder.name(TOPIC + "_CATE_UPDATE").partitions(1).replicas(3).build();
//	}
//	@Bean
//	NewTopic createServiceTopic() {
//		return TopicBuilder.name(TOPIC + "_SERVICE_CREATE").partitions(1).replicas(3).build();
//	}
//	@Bean
//	NewTopic updateServiceTopic() {
//		return TopicBuilder.name(TOPIC + "_SERVICE_UPDATE").partitions(1).replicas(3).build();
//	}
}
