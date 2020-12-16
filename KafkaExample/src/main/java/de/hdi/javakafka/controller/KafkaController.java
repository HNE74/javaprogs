package de.hdi.javakafka.controller;

import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hdi.javakafka.consumer.MyTopicConsumer;

/**
 * Rest controller for producing and consuming messages triggered by HTTP requests.
 * @author 057530
 */
@RestController
public class KafkaController {
	
	private final KafkaTemplate<String, String> template;
	private final MyTopicConsumer myTopicConsumer;
	
	/**
	 * Constructor.
	 * http://localhost:8080/kafka/produce?message=Test3
	 * @param template
	 * @param myTopicConsumer
	 */
	public KafkaController(KafkaTemplate<String, String> template, MyTopicConsumer myTopicConsumer) {
		this.template = template;
		this.myTopicConsumer = myTopicConsumer;
	}
	
	/**
	 * Produces a message for Kafka topic: myTopic.
	 * http://localhost:8080/kafka/consume
	 * @param message
	 */
	@GetMapping("kafka/produce")
	public void produce(@RequestParam String message) {
		template.send("myTopic", message);
	}
	
	/**
	 * Returns list of messages consumed from Kafka topic: myTopic
	 * @return
	 */
	@GetMapping("kafka/consume")
	public List<String> consume() {
		return myTopicConsumer.getMessages();
	}

}
