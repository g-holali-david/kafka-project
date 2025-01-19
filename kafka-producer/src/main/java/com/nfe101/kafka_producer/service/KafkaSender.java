package com.nfe101.kafka_producer.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nfe101.kafka_producer.model.TaxiStationJson;

@Service
public class KafkaSender {

    private static final Logger log = LoggerFactory.getLogger(KafkaSender.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final String stationsTopicName;
    private final Boolean syncSending;
    private final KafkaProducer<String, String> kafkaProducer;


    public KafkaSender(@Value("${stations.kafka-server}") String kafkaServers,
                       @Value("${stations.raw-kafka-topic}") String stationsTopicName,
                       @Value("${stations.sync-sending:false}") Boolean syncSending) {
        this.stationsTopicName = stationsTopicName;
        this.syncSending = syncSending;

        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        this.kafkaProducer = new KafkaProducer<>(properties);
    }


    public void sendStation(TaxiStationJson station) {

        String json;
        try {
            log.debug("Serializing java object to json");
            json = objectMapper.writeValueAsString(station);
            log.trace("Resultant Json: {}", json);
        } catch (JsonProcessingException e) {
            log.error("Could not serialize station object {} to json", station, e);
            throw new RuntimeException("Error serializing json from object: " + station + ". Cause: " + e, e);
        }
        String key = "raw-station-" + station.getId();
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(stationsTopicName, key, json);
        try {

            if(syncSending) {
                Future<RecordMetadata> sendingRecord = kafkaProducer.send(producerRecord);
                RecordMetadata sentRecord = sendingRecord.get();
                log.debug("Json was sent successfully to kafka topic: {}", sentRecord);
            } else {
                kafkaProducer.send(producerRecord, (sentRecord, error) -> {
                    if(error != null) {
                        log.error("Could not send json to kafka topic: {}", stationsTopicName, error);
                    } else {
                        log.debug("Json was sent successfully to kafka topic: {}", sentRecord);
                    }
                });
            }

        } catch (Exception e) {
            log.error("Could not send json to kafka topic: {}", stationsTopicName, e);
            throw new RuntimeException("Could not send json to kafka topic: " + stationsTopicName + ". Cause: " + e, e);
        }

    }
}
