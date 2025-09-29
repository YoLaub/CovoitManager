package com.covoiturage.repository;

import com.covoiturage.model.Covoitureur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CovoitureurRepository extends JpaRepository<Covoitureur, Long> {
    // Exemple : rechercher par email
    // Optional<Covoitureur> findByEmail(String email);
}
