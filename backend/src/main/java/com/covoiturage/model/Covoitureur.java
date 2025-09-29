package com.covoiturage.model;
import jakarta.persistence.*;

@Entity
@Table(name = "covoitureurs")
public class Covoitureur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String email;
    private double solde;

    public Covoitureur() {
    }

    public Covoitureur(Long id, String nom, String email, double solde) {
        this.id = id;
        this.nom = nom;
        this.email = email;
        this.solde = solde;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }
}
