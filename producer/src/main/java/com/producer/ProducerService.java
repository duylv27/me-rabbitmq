package com.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;

    public ProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String routingKey, String message) {
        rabbitTemplate.convertAndSend(
                "topic-inventory-master",
                routingKey,
                message
        );
        System.out.printf("Sent with Exchange: %s%n", rabbitTemplate.getExchange());
    }

    public void fanoutMessage(String message) {
        try {
            String sentMessage = "fanout " + message;
            rabbitTemplate.convertAndSend(
                    "fanout-exchange-inventory",
                    "",
                    sentMessage
            );
            System.out.printf("Message '%s' sent!%n", sentMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
