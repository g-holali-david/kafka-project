package com.nfe101.kafka_consumer_api.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nfe101.kafka_consumer_api.models.TaxiStationEntity;
import com.nfe101.kafka_consumer_api.models.TaxiStationJson;
import com.nfe101.kafka_consumer_api.repositories.TaxiStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @Autowired
    private TaxiStationRepository taxiStationRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "taxi-stations.raw", groupId = "taxi-stations-group")
    public void consumeMessage(String message) {
        try {
            // Désérialisation du message JSON
            TaxiStationJson taxiStation = objectMapper.readValue(message, TaxiStationJson.class);

            // Conversion en entité JPA
            TaxiStationEntity entity = new TaxiStationEntity();
            entity.setId(taxiStation.getId());
            entity.setName(taxiStation.getName());
            entity.setInsee(taxiStation.getInsee());
            entity.setAddress(taxiStation.getAddress());
            entity.setEmplacements(taxiStation.getEmplacements());
            entity.setStatus(taxiStation.getStatus());
            entity.setLatitude(taxiStation.getLatitude());
            entity.setLongitude(taxiStation.getLongitude());

            // Sauvegarde dans la base de données
            taxiStationRepository.save(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
