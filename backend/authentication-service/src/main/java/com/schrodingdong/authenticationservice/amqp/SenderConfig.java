package com.schrodingdong.authenticationservice.amqp;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderConfig {
    @Value("${amqp.auth-validation.queue.response}")
    private String queueAuthValidationName;
    @Value("${amqp.auth-validation.exchange.response}")
    private String exchangeAuthValidationName;
    @Value("${amqp.user-manager-registration.queue}")
    private String queueRegistrationName;
    @Value("${amqp.user-manager-registration.exchange}")
    private String exchangeRegistrationName;
    private boolean isDurable = false;

    
    //----------------------------------------------------------------------
    //-- For Authentication : Authentication-Service
    //----------------------------------------------------------------------
    
    @Bean("directAuth")
    DirectExchange directAuth() {
        return new DirectExchange(exchangeAuthValidationName);
    }
    @Bean("queueAuthRes")
    Queue queueAuthRes() {
        return new Queue(queueAuthValidationName, isDurable);
    }
    @Bean("bidingAuth")
    Binding bindingAuth(@Qualifier("directAuth") DirectExchange direct, @Qualifier("queueAuthRes") Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(direct)
                .with(queueAuthValidationName);
    }
    
    //----------------------------------------------------------------------
    //-- For registration : User-Manager-Service
    //----------------------------------------------------------------------
    
    @Bean("directRegistration")
    DirectExchange directRegistration() {
    	return new DirectExchange(exchangeRegistrationName);
    }
    @Bean("queueRegistration")
    Queue queueRegistration() {
    	return new Queue(queueRegistrationName, isDurable);
    }
    @Bean("bindingRegistration")
    Binding bindingRegistration(@Qualifier("directRegistration") DirectExchange direct, @Qualifier("queueRegistration") Queue queue) {
    	return BindingBuilder
    			.bind(queue)
    			.to(direct)
    			.with(queueRegistrationName);
    }
}
