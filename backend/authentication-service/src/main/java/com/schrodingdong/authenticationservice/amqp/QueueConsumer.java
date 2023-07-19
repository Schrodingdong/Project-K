package com.schrodingdong.authenticationservice.amqp;

import com.schrodingdong.authenticationservice.services.AuthService;
import com.schrodingdong.authenticationservice.utils.jwt.JwtUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {
    @Autowired
    private AuthService authService;
    @Autowired
    private QueueSender queueSender;

    @RabbitListener(queues = {"${amqp.auth-validation.queue.request}"})
    public void validateReceivedJwt(@Payload String jwtToken) {
        try {
            authService.validateToken(jwtToken);
        } catch (RuntimeException e) {
            //Not Valid
            queueSender.send(Boolean.toString(false));
            return;
        }
        //Valid
        queueSender.send(Boolean.toString(true));
    }

}