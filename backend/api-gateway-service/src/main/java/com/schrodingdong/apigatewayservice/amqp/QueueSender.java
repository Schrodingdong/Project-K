package com.schrodingdong.apigatewayservice.amqp;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class QueueSender {
    @Autowired
    private DirectExchange direct;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${amqp.auth-validation.queue.request}")
    private String routingKey;

    public void send(String message) {
        rabbitTemplate.convertAndSend(direct.getName(), routingKey, message);
    }
}
