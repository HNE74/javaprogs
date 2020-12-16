package de.hdi.javakafka.consumer;

import java.util.ArrayList;
import java.util.List;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Consumer of the topic: myTopic
 * @author 057530
 */
@Component
public class MyTopicConsumer {
	
	private final List<String> messages = new ArrayList<>();
	
	/**
	 * Listener for topic: myTopic in group kafka-sandbox
	 * @param message
	 */
	@KafkaListener(topics="myTopic", groupId="kafka-sandbox") 
	public void listen(String message) {
		synchronized(messages) {
			messages.add(message);
		}
	}
	
	public List<String> getMessages() {
		return this.messages;
	}

}
