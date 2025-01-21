package com.nfe101.kafka_streams.processor;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class TaxiStationStreamProcessor {

    private static final Logger logger = LoggerFactory.getLogger(TaxiStationStreamProcessor.class);

    public void processStreams(Properties kafkaStreamsProperties) {
        StreamsBuilder builder = new StreamsBuilder();
        ObjectMapper objectMapper = new ObjectMapper();

        KStream<String, String> sourceStream = builder.stream("taxi-stations.raw");

        // Regroupement par statut
        KTable<String, Long> groupedByStatus = sourceStream
                .mapValues(value -> {
                    try {
                        JsonNode jsonNode = objectMapper.readTree(value);
                        return jsonNode.get("status").asText();
                    } catch (Exception e) {
                        logger.error("Error parsing status: {}", e.getMessage());
                        return "ERROR";
                    }
                })
                .groupBy((key, status) -> status)
                .count(Materialized.as("status-counts"));

        groupedByStatus.toStream()
                .mapValues(value -> String.valueOf(value))
                .to("stations.grouped.by.status", Produced.with(Serdes.String(), Serdes.String()));


        // Filtrage pour Paris
        sourceStream.filter((key, value) -> {
            try {
                JsonNode jsonNode = objectMapper.readTree(value);
                String address = jsonNode.get("address").asText();
                return address.toUpperCase().contains("PARIS");
            } catch (Exception e) {
                logger.error("Error parsing address: {}", e.getMessage());
                return false;
            }
        }).to("paris.stations", Produced.with(Serdes.String(), Serdes.String()));

        KafkaStreams streams = new KafkaStreams(builder.build(), kafkaStreamsProperties);
        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
    }
}

