package com.nfe101.kafka_consumer_api.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class TaxiBorne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom, insee, adresse, noAppel, info, statut, geoShape, geoPoint, geoPointDatagouv;
    private Integer emplacements;

    // Getters


    public Long getId() {
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

    public Integer getEmplacements() {
        return emplacements;
    }

    // Getters et setters


    public void setId(Long id) {
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

    public void setEmplacements(Integer emplacements) {
        this.emplacements = emplacements;
    }
}
