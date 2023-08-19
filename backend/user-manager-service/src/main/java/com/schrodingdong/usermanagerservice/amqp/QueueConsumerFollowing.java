package com.schrodingdong.usermanagerservice.amqp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.schrodingdong.usermanagerservice.model.UserModel;
import com.schrodingdong.usermanagerservice.serivce.UserService;

@Component
public class QueueConsumerFollowing {
	private final Logger LOG = LoggerFactory.getLogger(QueueConsumerFollowing.class);
	@Autowired
	private UserService userService;
	@Autowired
	private ObjectMapper objectMapper;
	
	@RabbitListener(queues = {"${amqp.user-following.queue}"})
	public Message getFollowingListOfser(@Payload Message userEmailMessage) {
		try {
			// process the request and extract the userEmail
			String jsonReq = new String(userEmailMessage.getBody());
			Map<String,String> mapReq = objectMapper.readValue(jsonReq, Map.class);
			// get the following list
			List<UserModel> followingList = userService.getFollowingList(mapReq.get("userEmail"));
			// put it in JSON and return it
			String jsonRes = objectMapper.writeValueAsString(followingList);
			Message res = new Message(jsonRes.getBytes());
			return res;
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
