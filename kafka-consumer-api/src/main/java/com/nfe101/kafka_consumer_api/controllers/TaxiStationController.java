package com.nfe101.kafka_consumer_api.controllers;

import com.nfe101.kafka_consumer_api.models.TaxiStationEntity;
import com.nfe101.kafka_consumer_api.repositories.TaxiStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // Endpoint pour obtenir les stations avec pagination
    @GetMapping("/paginated")
    public ResponseEntity<Page<TaxiStationEntity>> getStationsWithPagination(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        // Créer un objet Pageable avec tri
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Récupérer les stations avec pagination
        Page<TaxiStationEntity> stations = taxiStationRepository.findAll(pageable);
        return ResponseEntity.ok(stations);
    }

    // Endpoint pour obtenir une station par ID
    @GetMapping("/{id}")
    public ResponseEntity<TaxiStationEntity> getStationById(@PathVariable String id) {
        Optional<TaxiStationEntity> station = taxiStationRepository.findById(id);
        return station.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
