package com.schoolar.lynx.notification.producer;

import com.schoolar.lynx.events.classes.ClassCreatedEvent;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.core.JacksonException;
import com.schoolar.lynx.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ClassCreatedProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public ClassCreatedProducer(
            RabbitTemplate rabbitTemplate,
            ObjectMapper objectMapper
    ) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(ClassCreatedEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.STUDY_QUEUE,
                    message
            );
        } catch (JacksonException e) {
            throw new IllegalStateException(
                    "Erro ao serializar o evento de criação da turma",
                    e
            );
        }
    }
}
