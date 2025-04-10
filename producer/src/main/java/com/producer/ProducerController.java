package com.producer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/producer")
public class ProducerController {

    private final ProducerService producerService;

    public ProducerController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/send")
    public String sendMessage(@RequestParam String message) {
        producerService.sendMessage("", message);
        return "Message sent: " + message;
    }

    @PostMapping("/fanout")
    public String fanoutMessage(@RequestParam String message) {
        producerService.fanoutMessage(message);
        return "Message fanout: " + message;
    }
}
