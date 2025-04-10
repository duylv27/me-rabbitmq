package com.producer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProducerService producerService) {
        return args -> {
            while (true) {
                Thread.sleep(3000);
                var message = "Product 1 update at %s".formatted(LocalDateTime.now());
                producerService.sendMessage("duy.inventory.test", message);
                producerService.sendMessage("duy.inventory.test", message);
                producerService.fanoutMessage(message);
            }
        };
    }
}