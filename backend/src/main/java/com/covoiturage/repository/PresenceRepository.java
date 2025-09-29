package com.covoiturage.repository;

import com.covoiturage.model.Presence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PresenceRepository extends JpaRepository<Presence, Long> {
    // Exemple : lister toutes les présences d’un covoitureur
    // List<Presence> findByCovoitureurId(Long covoitureurId);

    List<Presence> findByTrajetIdAndDate(Long trajetId, LocalDate date);

    boolean existsByTrajetIdAndCovoitureurIdAndDate(Long trajetId, Long covoitureurId, LocalDate date);
}
