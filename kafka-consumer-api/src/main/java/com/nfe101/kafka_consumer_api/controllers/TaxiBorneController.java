package com.nfe101.kafka_consumer_api.controllers;

import com.nfe101.kafka_consumer_api.models.TaxiBorne;
import com.nfe101.kafka_consumer_api.repositories.TaxiBorneRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bornes")
public class TaxiBorneController {
    private final TaxiBorneRepository repository;

    public TaxiBorneController(TaxiBorneRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<TaxiBorne> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaxiBorne> getById(@PathVariable Long id) {
        return repository.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public TaxiBorne create(@RequestBody TaxiBorne borne) {
        return repository.save(borne);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaxiBorne> update(@PathVariable Long id, @RequestBody TaxiBorne borneDetails) {
        return repository.findById(id).map(borne -> {
            borne.setNom(borneDetails.getNom());
            repository.save(borne);
            return ResponseEntity.ok(borne);
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return repository.findById(id).map(borne -> {
            repository.delete(borne);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
