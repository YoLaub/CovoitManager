package com.covoiturage.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "presences")
public class Presence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private boolean present;

    @ManyToOne
    @JoinColumn(name = "covoitureur_id")
    private Covoitureur covoitureur;

    @ManyToOne
    @JoinColumn(name = "trajet_id")
    private Trajet trajet;

    public Presence() {
    }

    public Presence(Long id, LocalDate date, boolean present, Covoitureur covoitureur, Trajet trajet) {
        this.id = id;
        this.date = date;
        this.present = present;
        this.covoitureur = covoitureur;
        this.trajet = trajet;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public Covoitureur getCovoitureur() {
        return covoitureur;
    }

    public void setCovoitureur(Covoitureur covoitureur) {
        this.covoitureur = covoitureur;
    }

    public Trajet getTrajet() {
        return trajet;
    }

    public void setTrajet(Trajet trajet) {
        this.trajet = trajet;
    }
}
