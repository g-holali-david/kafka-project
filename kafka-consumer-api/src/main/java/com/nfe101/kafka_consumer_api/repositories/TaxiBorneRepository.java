package com.nfe101.kafka_consumer_api.repositories;

import com.nfe101.kafka_consumer_api.models.TaxiBorne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaxiBorneRepository extends JpaRepository<TaxiBorne, Long> {
}
