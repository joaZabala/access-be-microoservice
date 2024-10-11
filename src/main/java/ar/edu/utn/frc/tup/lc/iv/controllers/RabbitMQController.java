package ar.edu.utn.frc.tup.lc.iv.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.tup.lc.iv.queues.RabbitMQSender;

@RestController
public class RabbitMQController {

    private final RabbitMQSender rabbitMQSender;

    public RabbitMQController(RabbitMQSender rabbitMQSender) {
        this.rabbitMQSender = rabbitMQSender;
    }

    @GetMapping("/send")
    public String send() {
        String message = "Hello, RabbitMQ!";
        rabbitMQSender.sendMessage(message);
        return "Message sent to RabbitMQ!";
    }
}