package com.nfe101.kafka_streams.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nfe101.kafka_streams.models.TaxiBorne;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.util.Properties;

@Service
public class KafkaStreamsService {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${kafka.input-topic}")
    private String inputTopic;

    @Value("${kafka.output-topic}")
    private String outputTopic;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void startStreams() {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("application.id", "kafka-streams-taxi-borne");

        StreamsBuilder builder = new StreamsBuilder();

        // 1. Lecture des messages du topic d'entrée
        KStream<String, String> inputStream = builder.stream(inputTopic);

        // 2. Transformation : compter les bornes par statut
        KTable<String, Long> aggregatedCounts = inputStream
                .mapValues(value -> {
                    try {
                        TaxiBorne borne = objectMapper.readValue(value, TaxiBorne.class);
                        return borne.getStatut();
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "inconnu";
                    }
                })
                .groupBy((key, statut) -> statut)
                .count();

        // 3. Écriture des résultats dans le topic de sortie
        aggregatedCounts.toStream()
                .mapValues(String::valueOf)
                .to(outputTopic);

        // 4. Construction et démarrage des streams
        Topology topology = builder.build();
        KafkaStreams streams = new KafkaStreams(topology, props);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
        System.out.println("Kafka Streams démarré.");
    }
}
