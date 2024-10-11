package ar.edu.utn.frc.tup.lc.iv.queues;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import ar.edu.utn.frc.tup.lc.iv.configs.RabbitMQConfig;

@Service
public class RabbitMQReceiver {
    
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}