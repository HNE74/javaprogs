package de.hdi.javakafka.configuration;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

/**
 * Provide Kafka message consumers.
 * @author 057530
 */
@Configuration
public class ConsumerConfiguration {
	
	private static final String KAFKA_BROKER = "localhost:9092";
	private static final String GROUP_ID = "kafka-sandbox";
	
	/**
	 * Creates factory class for consumers.
	 * @return
	 */
	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerConfigurations());
	}
	
	/**
	 * Provides consumer configuration.
	 * @return
	 */
	@Bean
	public Map<String, Object> consumerConfigurations() {
		Map<String, Object> configurations = new HashMap<>();
		configurations.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BROKER);
		configurations.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
		configurations.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configurations.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		
		return configurations;
	}
	
	/**
	 * Provides a factory for concurrent Kafka topic listener.
	 * @return
	 */
	@Bean
	ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory =
				new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}
}
