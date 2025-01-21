package com.nfe101.kafka_streams;

import com.nfe101.kafka_streams.processor.TaxiStationStreamProcessor;
import com.nfe101.kafka_streams.utils.KafkaTopicCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

@SpringBootApplication
public class KafkaStreamsApplication {

	@Autowired
	private KafkaTopicCreator kafkaTopicCreator;

	@Autowired
	private TaxiStationStreamProcessor taxiStationStreamProcessor;

	@Autowired
	private Properties kafkaStreamsProperties;

	public static void main(String[] args) {
		SpringApplication.run(KafkaStreamsApplication.class, args);
	}

	@PostConstruct
	public void init() {
		// Création des topics
		List<String> topics = Arrays.asList("taxi-stations.raw", "stations.grouped.by.status", "paris.stations");
		kafkaTopicCreator.createTopics(topics);

		// Lancer le traitement Kafka Streams
		taxiStationStreamProcessor.processStreams(kafkaStreamsProperties);
	}
}
