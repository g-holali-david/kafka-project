package com.nfe101.kafka_consumer_api.models;

public class TaxiStationJson {
    private String id;
    private String name;
    private String insee;
    private String address;
    private String emplacements;
    private String status;
    private Double latitude;
    private Double longitude;

    // Getters et setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getInsee() { return insee; }
    public void setInsee(String insee) { this.insee = insee; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmplacements() { return emplacements; }
    public void setEmplacements(String emplacements) { this.emplacements = emplacements; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
