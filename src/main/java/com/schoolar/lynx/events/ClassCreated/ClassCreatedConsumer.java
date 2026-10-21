package com.schoolar.lynx.events.ClassCreated;

import com.schoolar.lynx.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ClassCreatedConsumer {

    private final NotificationService notificationService;

    public ClassCreatedConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @RabbitListener(queues = RabbitMQConfig.STUDY_QUEUE)
    public void consume(String message) {
        System.out.println("Evento recebido: " + message);

        notificationService.notifyClassCreated(message);
    }
}