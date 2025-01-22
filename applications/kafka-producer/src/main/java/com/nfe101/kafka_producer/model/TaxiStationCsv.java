package com.nfe101.kafka_producer.model;
import com.opencsv.bean.CsvBindByName;

public class TaxiStationCsv {

    @CsvBindByName(column = "id")
    private String id;
    @CsvBindByName(column = "nom")
    private String name;
    @CsvBindByName(column = "insee")
    private String insee;
    @CsvBindByName(column = "adresse")
    private String address;
    @CsvBindByName(column = "emplacements")
    private String emplacements;
    @CsvBindByName(column = "statut")
    private String status;
    @CsvBindByName(column = "latitude")
    private Double latitude;
    @CsvBindByName(column = "longitude")
    private Double longitude;

    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInsee() {
        return insee;
    }

    public void setInsee(String insee) {
        this.insee = insee;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmplacements() {
        return emplacements;
    }

    public void setEmplacements(String emplacements) {
        this.emplacements = emplacements;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}