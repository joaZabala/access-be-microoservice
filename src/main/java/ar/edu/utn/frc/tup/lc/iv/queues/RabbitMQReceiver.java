package ar.edu.utn.frc.tup.lc.iv.queues;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.edu.utn.frc.tup.lc.iv.configs.RabbitMQConfig;
import ar.edu.utn.frc.tup.lc.iv.dtos.common.authorizedRanges.VisitorAuthRequest;
import ar.edu.utn.frc.tup.lc.iv.services.imp.AuthService;

@Service
public class RabbitMQReceiver {

    @Autowired
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String jsonMessage) {
        try {
            // Convert JSON string back to VisitorAuthRequest object
            VisitorAuthRequest visitorAuthRequest = objectMapper.readValue(jsonMessage, VisitorAuthRequest.class);
            // Process the visitorAuthRequest object as needed
            authService.authorizeVisitor(visitorAuthRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}