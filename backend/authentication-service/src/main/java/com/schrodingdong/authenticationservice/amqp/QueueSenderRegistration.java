package com.schrodingdong.authenticationservice.amqp;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class QueueSenderRegistration {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${amqp.user-manager-registration.queue}")
    private String routingKey;
    @Value("${amqp.user-manager-registration.exchange}")
    private String exchangeKey;

    public void send(String message) {
        rabbitTemplate.convertAndSend(exchangeKey, routingKey, message);
    }

}
