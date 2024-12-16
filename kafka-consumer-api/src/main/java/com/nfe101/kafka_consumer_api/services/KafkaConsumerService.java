package com.nfe101.kafka_consumer_api.services;

import com.nfe101.kafka_consumer_api.models.TaxiBorne;
import com.nfe101.kafka_consumer_api.repositories.TaxiBorneRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {
    private final TaxiBorneRepository repository;

    public KafkaConsumerService(TaxiBorneRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "${kafka.topic}", groupId = "taxi-group")
    public void consume(String message) {
        // Mapper le message JSON en TaxiBorne
        TaxiBorne borne = new TaxiBorne();
        // Déserialisation simplifiée ici (ou utilisez un ObjectMapper Jackson)
        repository.save(borne);
        System.out.println("Message consommé et enregistré : " + borne.getNom());
    }
}
