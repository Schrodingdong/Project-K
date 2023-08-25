package com.schrodingdong.configurationservice.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {
    @Value("${amqp.queue-names.auth-validation-req}")
    private String authValidationReq;
    @Value("${amqp.queue-names.auth-validation-res}")
    private String authValidationRes;
    @Value("${amqp.queue-names.quote-user-following-q}")
    private String quoteUserFollowingQ;

    @Bean
    public Queue authValidationReq() {
        return new Queue(authValidationReq);
    }
    @Bean
    public Queue authValidationRes() {
        return new Queue(authValidationRes);
    }
    @Bean
    public Queue quoteUserFollowingQ() {
        return new Queue(quoteUserFollowingQ);
    }
}
