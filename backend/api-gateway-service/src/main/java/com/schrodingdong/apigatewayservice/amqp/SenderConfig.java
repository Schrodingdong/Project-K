package com.schrodingdong.apigatewayservice.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderConfig {
    @Value("${amqp.auth-validation.queue.request}")
    private String queueName;
    @Value("${amqp.auth-validation.exchange.request}")
    private String exchangeName;
    private boolean isDurable = false;

    @Bean
    public DirectExchange direct() {
        return new DirectExchange(exchangeName);
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
