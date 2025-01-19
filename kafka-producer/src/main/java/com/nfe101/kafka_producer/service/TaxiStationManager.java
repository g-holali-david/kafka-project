package com.nfe101.kafka_producer.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.nfe101.kafka_producer.model.TaxiStationCsv;

@Service
public class TaxiStationManager {
    private static final Logger log = LoggerFactory.getLogger(TaxiStationManager.class);

    private final CsvReader csvReader;
    private final KafkaSender kafkaSender;

    public TaxiStationManager(CsvReader csvReader, KafkaSender kafkaSender) {
        this.csvReader = csvReader;
        this.kafkaSender = kafkaSender;
    }

    public void transfer(String csvFileName) {
        log.info("Transferring data from CSV file: {}", csvFileName);
        List<TaxiStationCsv> stations = csvReader.readStations(csvFileName);

        stations.stream()
                .map(csv -> TaxiStationMapper.toJson(csv))
                .forEach(station -> {
                    kafkaSender.sendStation(station);
                });
        log.info("Transfer complete.");
    }
}
