package com.schrodingdong.authenticationservice.amqp;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderConfig {
    @Value("${amqp.auth-validation.queue.response}")
    private String queueName;
    @Value("${amqp.auth-validation.queue.response}")
    private String exchangeName;
    private boolean isDurable = false;

    @Bean
    public DirectExchange direct() {
        return new DirectExchange("auth-validation-response-exchange");
    }
    @Bean
    public Queue queue() {
        return new Queue(queueName, isDurable);
    }

    @Bean
    public Binding binding(DirectExchange direct, Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(direct)
                .with(queueName);
    }
}
