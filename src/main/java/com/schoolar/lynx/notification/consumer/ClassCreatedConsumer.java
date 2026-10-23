package com.schoolar.lynx.notification.consumer;

import com.schoolar.lynx.config.RabbitMQConfig;
import com.schoolar.lynx.events.classes.ClassCreatedEvent;
import com.schoolar.lynx.notification.handler.ClassCreatedHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;

@Component
public class ClassCreatedConsumer {
    private final ObjectMapper objectMapper;
    private final ClassCreatedHandler classCreatedHandler;

    public ClassCreatedConsumer(
            ObjectMapper objectMapper,
            ClassCreatedHandler classCreatedHandler
    ) {
        this.objectMapper = objectMapper;
        this.classCreatedHandler = classCreatedHandler;
    }

    @RabbitListener(queues = RabbitMQConfig.STUDY_QUEUE)
    public void consume(String message) throws IOException {
        ClassCreatedEvent event =
                objectMapper.readValue(message, ClassCreatedEvent.class);

        classCreatedHandler.handleClassCreated(event);
    }
}