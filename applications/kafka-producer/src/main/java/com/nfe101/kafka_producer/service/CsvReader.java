package com.nfe101.kafka_producer.service;


import java.io.File;
import java.io.FileReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.opencsv.bean.CsvToBeanBuilder;
import com.nfe101.kafka_producer.model.TaxiStationCsv;

@Service
public class CsvReader {
    private static final Logger log = LoggerFactory.getLogger(CsvReader.class);

    private final String dataFolder;

    public CsvReader(@Value("${stations.data-folder}") String dataFolder) {
        this.dataFolder = dataFolder;
    }

    public List<TaxiStationCsv> readStations(String fileName) {
        String filePath = dataFolder + File.separator + fileName;
        log.debug("Parsing file: {}", filePath);
        try {
            return new CsvToBeanBuilder<TaxiStationCsv>(new FileReader(filePath))
                    .withType(TaxiStationCsv.class)
                    .build()
                    .parse();
        } catch (Exception e) {
            log.error("Could not parse file: {}", filePath, e);
            throw new RuntimeException("Could not parse CSV file: " + fileName + ". Cause: " + e, e);
        }
    }
}
