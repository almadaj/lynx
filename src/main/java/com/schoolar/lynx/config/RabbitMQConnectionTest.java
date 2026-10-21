package com.schoolar.lynx.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConnectionTest implements ApplicationRunner {

    private final AmqpAdmin amqpAdmin;

    public RabbitMQConnectionTest(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    @Override
    public void run(ApplicationArguments args) {
        System.out.println("AmqpAdmin: " + amqpAdmin.getClass().getName());
        System.out.println("Filas declaradas: " + amqpAdmin.getQueueProperties("lynx.study.queue"));
    }
}