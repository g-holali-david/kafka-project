package com.nfe101.kafka_consumer_api.controllers;

import com.nfe101.kafka_consumer_api.models.TaxiStationEntity;
import com.nfe101.kafka_consumer_api.repositories.TaxiStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/stations")
public class TaxiStationController {

    @Autowired
    private TaxiStationRepository taxiStationRepository;

    // Endpoint pour obtenir toutes les stations
    @GetMapping
    public ResponseEntity<List<TaxiStationEntity>> getAllStations() {
        List<TaxiStationEntity> stations = taxiStationRepository.findAll();
        return ResponseEntity.ok(stations);
    }

    // Endpoint pour obtenir une station par ID
    @GetMapping("/{id}")
    public ResponseEntity<TaxiStationEntity> getStationById(@PathVariable String id) {
        Optional<TaxiStationEntity> station = taxiStationRepository.findById(id);
        return station.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
