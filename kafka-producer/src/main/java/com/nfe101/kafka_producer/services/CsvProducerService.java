package com.nfe101.kafka_producer.services;

import com.nfe101.kafka_producer.dto.TaxiBorneDTO;
import com.opencsv.CSVReader;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.FileReader;

@Service
public class CsvProducerService {
    private final KafkaTemplate<String, TaxiBorneDTO> kafkaTemplate;

    @Value("${kafka.topic}")
    private String topic;

    @Value("${csv.filepath}")
    private String csvFilePath;

    public CsvProducerService(KafkaTemplate<String, TaxiBorneDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostConstruct
    public void produceCsvToKafka() {
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextRecord;
            csvReader.readNext(); // Skip the header
            while ((nextRecord = csvReader.readNext()) != null) {
                TaxiBorneDTO borne = mapToDto(nextRecord);
                kafkaTemplate.send(topic, borne);
                System.out.println("Sent: " + borne.getNom());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private TaxiBorneDTO mapToDto(String[] record) {
        TaxiBorneDTO dto = new TaxiBorneDTO();
        dto.setId(record[0]);
        dto.setNom(record[1]);
        dto.setInsee(record[2]);
        dto.setAdresse(record[3]);
        dto.setEmplacements(Integer.parseInt(record[4]));
        dto.setNoAppel(record[5]);
        dto.setInfo(record[6]);
        dto.setStatut(record[7]);
        dto.setGeoShape(record[8]);
        dto.setGeoPoint(record[9]);
        dto.setGeoPointDatagouv(record[10]);
        return dto;
    }
}