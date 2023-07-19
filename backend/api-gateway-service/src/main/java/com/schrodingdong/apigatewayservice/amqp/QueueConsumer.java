package com.schrodingdong.apigatewayservice.amqp;

import com.schrodingdong.apigatewayservice.configuration.JwtValidationGatewayFilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class QueueConsumer {
    Logger LOG = LoggerFactory.getLogger(QueueConsumer.class);
    @RabbitListener(queues = {"${amqp.auth-validation.queue.response}"})
    public void jwtValidationResponse(String jwtValidationResponse) {
        var isValid = Boolean.parseBoolean(jwtValidationResponse);
        LOG.info("JWT Validation Response: " + isValid);
        JwtValidationGatewayFilterFactory.setIsJwtValid(isValid);
    }
}
