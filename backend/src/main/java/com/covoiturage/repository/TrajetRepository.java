package com.covoiturage.repository;

import com.covoiturage.model.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrajetRepository extends JpaRepository<Trajet, Long> {
    // Ici tu peux ajouter des méthodes personnalisées si besoin
}
