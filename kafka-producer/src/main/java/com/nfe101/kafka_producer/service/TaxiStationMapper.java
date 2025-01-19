package com.nfe101.kafka_producer.service;

import com.nfe101.kafka_producer.model.TaxiStationCsv;
import com.nfe101.kafka_producer.model.TaxiStationJson;

public class TaxiStationMapper {

    public static TaxiStationJson toJson(TaxiStationCsv csv) {
        TaxiStationJson json = new TaxiStationJson();

        json.setId(csv.getId());
        json.setName(csv.getName());
        json.setInsee(csv.getInsee());
        json.setAddress(csv.getAddress());
        json.setEmplacements(csv.getEmplacements());
        json.setStatus(csv.getStatus());
        json.setLatitude(csv.getLatitude());
        json.setLongitude(csv.getLongitude());

        return json;
    }
}

