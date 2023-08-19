package com.schrodingdong.authenticationservice.amqp;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueSenderAuthValidation {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${amqp.auth-validation.queue.response}")
    private String routingKey;
    @Value("${amqp.auth-validation.exchange.response}")
    private String exchangeKey;

    public void send(String message) {
        rabbitTemplate.convertAndSend(exchangeKey, routingKey, message);
    }
    
}
