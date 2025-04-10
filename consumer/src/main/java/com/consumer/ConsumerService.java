package com.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    @RabbitListener(queues = "my-queue")
    public void receiveMessage(String message) {
        System.out.println("Received: " + message);
    }

    @RabbitListener(queues = "fanout-inventory-vn")
    public void receiveFanoutMessage1(String message) {
        System.out.println("[fanout-inventory-vn] - Received: " + message);
    }

    @RabbitListener(queues = "fanout-inventory-us")
    public void receiveFanoutMessage2(String message) {
        System.out.println("[fanout-inventory-us] - Received: " + message);
    }

    @RabbitListener(queues = "fanout-inventory-uk")
    public void receiveFanoutMessage3(String message) {
        System.out.println("[fanout-inventory-uk] - Received: " + message);
    }
}
