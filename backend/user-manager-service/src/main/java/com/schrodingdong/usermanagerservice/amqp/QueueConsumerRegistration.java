package com.schrodingdong.usermanagerservice.amqp;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.schrodingdong.usermanagerservice.serivce.UserService;
@Component
public class QueueConsumerRegistration {
	@Autowired
	private UserService userService;

    @RabbitListener(queues = {"${amqp.user-manager-registration.queue}"})
    public void validateReceivedJwt(@Payload String jsonToDecode) {
    	JSONObject registrationJson = (JSONObject) JSONValue.parse(jsonToDecode);
    	String initialBio = "";
    	userService.createUser(
    			(String) registrationJson.get("userEmail"), 
    			(String) registrationJson.get("username"), 
    			initialBio
    	);
    }
}
