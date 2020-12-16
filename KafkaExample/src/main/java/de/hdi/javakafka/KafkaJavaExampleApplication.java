package de.hdi.javakafka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Kafka Spring Boot sandbox app.
 * 1) Before starting install Kafka to your local machine: https://kafka.apache.org/
 * 2) Go to directory %KAFKA_HOME%/bin/windows to access the batch scripts.
 * 3) Start zookeeper: zookeeper-server-start.bat ..\\..\\config\\zookeeper.properties
 * 4) Start the Kafka broker: kafka-server-start.bat ..\\..\\config\\server.properties
 * 5) Create a topic: kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic myTopic
 * 
 * Send and receive messages by calling the services provided by the REST-controller class.
 * @author 057530
 */
@SpringBootApplication
public class KafkaJavaExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaJavaExampleApplication.class, args);
	}

}
