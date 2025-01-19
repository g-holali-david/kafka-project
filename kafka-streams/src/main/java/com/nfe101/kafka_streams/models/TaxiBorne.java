package com.nfe101.kafka_streams.models;

public class TaxiBorne {
    private String id;
    private String nom;
    private String statut;

    // Getters et setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
}

