package com.adminservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import com.adminservice.service.OrderPlacedEvent;

@SpringBootApplication
public class AdminserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminserviceApplication.class, args);
	}
	
	@KafkaListener(topics = "notificationTopic")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        // Send out an email notification
        System.out.println("Received Notification for Order - {}"+ orderPlacedEvent.getOrderId());
    }
}
