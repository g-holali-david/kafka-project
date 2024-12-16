package com.nfe101.kafka_producer.dto;

public class TaxiBorneDTO {
    private String id;
    private String nom;
    private String insee;
    private String adresse;
    private Integer emplacements;
    private String noAppel;
    private String info;
    private String statut;
    private String geoShape;
    private String geoPoint;
    private String geoPointDatagouv;


    // Getter
    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getInsee() {
        return insee;
    }

    public String getAdresse() {
        return adresse;
    }

    public Integer getEmplacements() {
        return emplacements;
    }

    public String getNoAppel() {
        return noAppel;
    }

    public String getInfo() {
        return info;
    }

    public String getStatut() {
        return statut;
    }

    public String getGeoShape() {
        return geoShape;
    }

    public String getGeoPoint() {
        return geoPoint;
    }

    public String getGeoPointDatagouv() {
        return geoPointDatagouv;
    }

    // Setter
    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setInsee(String insee) {
        this.insee = insee;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEmplacements(Integer emplacements) {
        this.emplacements = emplacements;
    }

    public void setNoAppel(String noAppel) {
        this.noAppel = noAppel;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setGeoShape(String geoShape) {
        this.geoShape = geoShape;
    }

    public void setGeoPoint(String geoPoint) {
        this.geoPoint = geoPoint;
    }

    public void setGeoPointDatagouv(String geoPointDatagouv) {
        this.geoPointDatagouv = geoPointDatagouv;
    }
}