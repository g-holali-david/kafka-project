package com.nfe101.kafka_consumer_api.repositories;

import com.nfe101.kafka_consumer_api.models.TaxiStationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiStationRepository extends JpaRepository<TaxiStationEntity, String> {
}
