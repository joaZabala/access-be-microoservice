package ar.edu.utn.frc.tup.lc.iv.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.queues.RabbitMQSender;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthService;

@RestController
public class RabbitMQController {

    private final RabbitMQSender rabbitMQSender;

    @Autowired
    private AuthService authService;

    public RabbitMQController(RabbitMQSender rabbitMQSender) {
        this.rabbitMQSender = rabbitMQSender;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody VisitorAuthRequest visitorAuthRequest) {
        rabbitMQSender.sendMessage(visitorAuthRequest);
        return ResponseEntity.ok("Visitante enviado con exito!");
    }
}