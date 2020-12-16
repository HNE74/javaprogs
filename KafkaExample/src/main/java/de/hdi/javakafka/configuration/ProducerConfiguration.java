package de.hdi.javakafka.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

/**
 * Provides Kafka message producers.
 * @author 057530
 */
@Configuration
public class ProducerConfiguration {
	
	private static final String KAFKA_BROKER = "localhost:9092";
	
	/**
	 * Returns a producer factory for creating instance of Kafka message producers.
	 * @return
	 */
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigurations());
	}
	
	/**
	 * Defines the configuration used to generate message producers.
	 * @return
	 */
	@Bean
	public Map<String, Object> producerConfigurations() {
		Map<String, Object> configurations = new HashMap<>();
		configurations.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER);
		configurations.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configurations.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		return configurations;
	}
	
	/**
	 * A template is used to perform operations on the message producer (e.g. sending a message).
	 * @return
	 */
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

}
