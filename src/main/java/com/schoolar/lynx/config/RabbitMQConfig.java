package com.schoolar.lynx.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String STUDY_QUEUE = "lynx.study.queue";

    @Bean
    public Queue studyQueue() {
        return new Queue(STUDY_QUEUE, true);
    }
}
