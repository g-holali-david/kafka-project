package com.nfe101.kafka_streams.utils;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

@Component
public class KafkaTopicCreator {

    private static final Logger logger = LoggerFactory.getLogger(KafkaTopicCreator.class);
    private final AdminClient adminClient;

    public KafkaTopicCreator(Properties kafkaStreamsProperties) {
        this.adminClient = AdminClient.create(kafkaStreamsProperties);
    }

    public void createTopics(List<String> topics) {
        try {
            List<NewTopic> newTopics = topics.stream()
                    .map(topic -> new NewTopic(topic, 1, (short) 1)) // 1 partition, réplication facteur 1
                    .toList();
            adminClient.createTopics(newTopics);
            logger.info("Topics created successfully: {}", topics);
        } catch (Exception e) {
            logger.error("Error creating topics: {}", e.getMessage());
        }
    }

    public void close() {
        adminClient.close();
    }
}
