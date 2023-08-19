package com.schrodingdong.quotemanagerservice.amqp;


import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SenderConfig {
	@Value("${amqp.user-following.queue}")
	private String queueUserManager;
	@Value("${amqp.user-following.exchange}")
	private String exchangeUserManager;
	private boolean isDurable = false;
	
	@Bean
	DirectExchange directQuote() {
		return new DirectExchange(exchangeUserManager);
	}
	@Bean
	Queue queueRequest() {
		return new Queue(queueUserManager, isDurable);
	}
	@Bean
	Binding bindingQuote(DirectExchange d,  Queue q) {
		return BindingBuilder
				.bind(q)
				.to(d)
				.with(queueUserManager);
	}
	
	@Bean
	RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}
}
