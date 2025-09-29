package com.covoiturage.service;

import com.covoiturage.model.Covoitureur;
import com.covoiturage.model.Presence;
import com.covoiturage.model.Trajet;
import com.covoiturage.repository.CovoitureurRepository;
import com.covoiturage.repository.PresenceRepository;
import com.covoiturage.repository.TrajetRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CovoiturageService {

    private final TrajetRepository trajetRepo;
    private final CovoitureurRepository covoitRepo;
    private final PresenceRepository presenceRepo;

    public CovoiturageService(TrajetRepository trajetRepo,
                              CovoitureurRepository covoitRepo,
                              PresenceRepository presenceRepo) {
        this.trajetRepo = trajetRepo;
        this.covoitRepo = covoitRepo;
        this.presenceRepo = presenceRepo;
    }

    // --- Trajets ---
    public Trajet addTrajet(Trajet trajet) {
        return trajetRepo.save(trajet);
    }

    public List<Trajet> getAllTrajets() {
        return trajetRepo.findAll();
    }

    public void deleteTrajet(Long id) {
        trajetRepo.deleteById(id);
    }

    // --- Covoitureurs ---
    public Covoitureur addCovoitureur(Covoitureur covoit) {
        return covoitRepo.save(covoit);
    }

    public List<Covoitureur> getAllCovoitureurs() {
        return covoitRepo.findAll();
    }

    public void deleteCovoitureur(Long id) {
        covoitRepo.deleteById(id);
    }

    // --- Présences ---
    @Transactional
    public Presence addPresences(Presence presence) {
        // Vérification trajet + covoitureur
        Trajet trajet = trajetRepo.findById(presence.getTrajet().getId())
                .orElseThrow(() -> new RuntimeException("Trajet introuvable"));
        Covoitureur covoitureur = covoitRepo.findById(presence.getCovoitureur().getId())
                .orElseThrow(() -> new RuntimeException("Covoitureur introuvable"));

        // On force les bonnes références
        presence.setTrajet(trajet);
        presence.setCovoitureur(covoitureur);
        if (presence.getDate() == null) {
            presence.setDate(LocalDate.now()); // fallback
        }
        if (presenceRepo.existsByTrajetIdAndCovoitureurIdAndDate(
                trajet.getId(), covoitureur.getId(), presence.getDate())) {
            throw new RuntimeException("Ce covoitureur est déjà inscrit à ce trajet pour cette date");
        }


        Presence savedPresence = presenceRepo.save(presence);

        List<Presence> presences = presenceRepo.findByTrajetIdAndDate(trajet.getId(), presence.getDate());


        if (!presences.isEmpty()) {
            double part = trajet.getPrix() / 2;
            System.out.println(part);

            Covoitureur cv = covoitureur;
            cv.setSolde(cv.getSolde() + part);
            covoitRepo.save(cv);
        }

        return savedPresence;
    }


    public List<Presence> getAllPresences() {
        return presenceRepo.findAll();
    }

    public void deletePresence(Long id) {
        presenceRepo.deleteById(id);
    }

    public Trajet updateTrajetPrix(Long trajetId, double nouveauPrix) {
        Trajet trajet = trajetRepo.findById(trajetId)
                .orElseThrow(() -> new RuntimeException("Trajet introuvable"));
        trajet.setPrix(nouveauPrix);
        return trajetRepo.save(trajet);
    }

    // Ajoute une présence et met à jour les soldes
    public Presence enregistrerPresence(Long trajetId, Long covoitureurId, LocalDate date) {
        boolean present = true;
        Trajet trajet = trajetRepo.findById(trajetId)
                .orElseThrow(() -> new RuntimeException("Trajet introuvable"));
        Covoitureur covoitureur = covoitRepo.findById(covoitureurId)
                .orElseThrow(() -> new RuntimeException("Covoitureur introuvable"));

        // Sauvegarde la présence
        Presence presence = new Presence();
        presence.setTrajet(trajet);
        presence.setCovoitureur(covoitureur);
        presence.setDate(date);
        presence.setPresent(present);
        presenceRepo.save(presence);

        // 🔥 Recalcule le prix par covoitureur pour ce trajet et cette date
        List<Presence> presences = presenceRepo.findAll()
                .stream()
                .filter(p -> p.getTrajet().getId().equals(trajetId) && p.getDate().equals(date))
                .toList();

        if (!presences.isEmpty()) {
            double part = trajet.getPrix() / presences.size();

            // Met à jour le solde de chaque covoitureur
            for (Presence p : presences) {
                Covoitureur cv = p.getCovoitureur();
                cv.setSolde(cv.getSolde() + part);
                covoitRepo.save(cv);
            }
        }

        return presenceRepo.save(presence);
    }



    // Remet à zéro les soldes de tous les covoitureurs
    public void resetAllSoldes() {
        List<Covoitureur> covoitureurs = covoitRepo.findAll();
        for (Covoitureur c : covoitureurs) {
            c.setSolde(0.0);
            covoitRepo.save(c);
        }
    }

    public double getSoldeByCovoitureurId(Long covoitId) {
        return covoitRepo.findById(covoitId)
                .map(Covoitureur::getSolde)
                .orElseThrow(() -> new RuntimeException("Covoitureur non trouvé"));
    }


}
