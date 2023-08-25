package com.schrodingdong.configurationservice.amqp;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AmqpService {
    private Logger LOG = LoggerFactory.getLogger(AmqpService.class);
    private final RabbitTemplate rabbitTemplate;
    private final Queue authValidationReq;
    private final Queue authValidationRes;
    private final Queue quoteUserFollowingQ;

    public void verifyQueues() throws AmqpException{
        Message m1 = rabbitTemplate.receive(authValidationReq.getName());
        Message m2 = rabbitTemplate.receive(authValidationRes.getName());
        Message m3 = rabbitTemplate.receive(quoteUserFollowingQ.getName());
        Map<String, Message> messages = new HashMap<>();
        messages.put(authValidationReq.getName(), m1);
        messages.put(authValidationRes.getName(), m2);
        messages.put(quoteUserFollowingQ.getName(), m3);
        for (Map.Entry m : messages.entrySet()) {
            // Check if it is null (should be null) or no messages
            if (m.getValue() == null || ((Message) m.getValue()).getMessageProperties().getMessageCount() == 0) {
                continue;
            }
            // resend it incase it is not
            rabbitTemplate.send((String) m.getKey(), (Message) m.getValue());
            // throw error
            throw new AmqpException("Problem in Queue creation");
        }
    }
}
