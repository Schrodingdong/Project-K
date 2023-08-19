package com.schrodingdong.quotemanagerservice.amqp;

import java.util.*;

import com.fasterxml.jackson.databind.JsonNode;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.schrodingdong.quotemanagerservice.model.UserModel;

@Component
public class QueueSenderFollowing {
	private final Logger LOG = LoggerFactory.getLogger(QueueSenderFollowing.class);
	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	@Value("${amqp.user-following.queue}")
	private String routingKey;
	@Value("${amqp.user-following.exchange}")
	private String exchangeKey;
	
	/***
	 * send a request to get user following list of the user
	 * @param userEmail the user email in json
	 * @return List of following, or null if there was a problem
	 */
	public List<UserModel> send(String userEmail) throws RuntimeException {
		try{
			// init the message to send
			Map<String, String> req = new HashMap<>();
			req.put("userEmail", userEmail);
			String jsonReq = objectMapper.writeValueAsString(req);
			LOG.info("Sending message to get user following list: " + jsonReq);
			Message message = new Message(jsonReq.getBytes());
			// sending the message
			Message res = rabbitTemplate.sendAndReceive(exchangeKey, routingKey, message);
			// extracting the response
			String jsonRes = new String(res.getBody());
			List<LinkedHashMap<String,String>> listRes = objectMapper.readValue(jsonRes, List.class);
			List<UserModel> followingUsersList = new ArrayList<>();
			for (LinkedHashMap<String,String> map : listRes) {
				followingUsersList.add(new UserModel(map.get("email"), map.get("username"), map.get("bio")));
			}
			LOG.info("Received message to get user following list: " + jsonRes);
			LOG.info("pre processing ?: " + listRes);
			LOG.info("post processing ?: " + followingUsersList);
			return followingUsersList;
		} catch (Exception e) {
			LOG.error("Error while sending message to get user following list: " + e.getMessage());
			throw new RuntimeException("Error retrieving the user following list");
		}
	}
}
