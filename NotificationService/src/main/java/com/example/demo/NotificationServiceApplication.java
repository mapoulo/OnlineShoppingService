package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import com.example.demo.Consumers.OrderEvent;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}
	
	
	@KafkaListener(topics = "noticationTopic")
	public void handleNotification(OrderEvent orderEvent) {
		log.info("Received notification for order - {} "+ orderEvent.getOrderNumber());
	}
	

}
