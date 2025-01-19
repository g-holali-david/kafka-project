package com.nfe101.kafka_producer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nfe101.kafka_producer.service.TaxiStationManager;

@RestController
@RequestMapping("/stations")
public class TaxiStationController {
    private final TaxiStationManager stationManager;

    public TaxiStationController(TaxiStationManager stationManager) {
        this.stationManager = stationManager;
    }

    @PostMapping
    public ResponseEntity<String> transfer(@RequestParam String file) {
        try {
            stationManager.transfer(file);
            return ResponseEntity.ok("File imported successfully.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}

