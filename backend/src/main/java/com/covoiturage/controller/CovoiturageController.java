package com.covoiturage.controller;

import com.covoiturage.model.Covoitureur;
import com.covoiturage.model.Presence;
import com.covoiturage.model.Trajet;
import com.covoiturage.service.CovoiturageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/covoiturage")
@CrossOrigin(origins = "http://localhost:5173")
public class CovoiturageController {

    private final CovoiturageService covoiturageService;

    public CovoiturageController(CovoiturageService covoiturageService) {
        this.covoiturageService = covoiturageService;
    }

    // --- Trajets ---
    @PostMapping("/trajet")
    public Trajet addTrajet(@RequestBody Trajet trajet) {
        return covoiturageService.addTrajet(trajet);
    }

    @GetMapping("/trajet")
    public List<Trajet> allTrajets() {
        return covoiturageService.getAllTrajets();
    }

    @DeleteMapping("/trajet/{id}")
    public void deleteTrajet(@PathVariable Long id) {
        covoiturageService.deleteTrajet(id);
    }

    // --- Covoitureurs ---
    @PostMapping("/covoitureur")
    public Covoitureur addCovoitureur(@RequestBody Covoitureur covoit) {
        return covoiturageService.addCovoitureur(covoit);
    }

    @GetMapping("/covoitureur")
    public List<Covoitureur> allCovoitureurs() {
        return covoiturageService.getAllCovoitureurs();
    }

    @DeleteMapping("/covoitureur/{id}")
    public void deleteCovoitureur(@PathVariable Long id) {
        covoiturageService.deleteCovoitureur(id);
    }

    // --- Présences ---
    @PostMapping("/presence")
    public ResponseEntity<?> addPresences(@RequestBody List<Presence> presences) {
        try {
            List<Presence> savedList = new ArrayList<>();
            for (Presence p : presences) {
                Presence saved = covoiturageService.addPresences(p);
                savedList.add(saved);
            }
            return ResponseEntity.ok(savedList); // renvoie toute la liste
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/presence")
    public List<Presence> allPresences() {
        return covoiturageService.getAllPresences();
    }

    @DeleteMapping("/presence/{id}")
    public void deletePresence(@PathVariable Long id) {
        covoiturageService.deletePresence(id);
    }

    // --- Gestion du prix du trajet ---
    @PutMapping("/trajet/{id}/prix")
    public Trajet updateTrajetPrix(@PathVariable Long id, @RequestParam double prix) {
        return covoiturageService.updateTrajetPrix(id, prix);
    }

    @PostMapping("/presence/{trajetId}/{covoitureurId}")
    public Presence addPresence(@PathVariable Long trajetId,
                                @PathVariable Long covoitureurId,
                                @RequestParam String date){
        return covoiturageService.enregistrerPresence(trajetId, covoitureurId, LocalDate.parse(date));
    }



    // Remettre tous les soldes à zéro
    @PutMapping("/covoitureur/reset-soldes")
    public String resetSoldes() {
        covoiturageService.resetAllSoldes();
        return "Tous les soldes ont été remis à zéro ✅";
    }


    @GetMapping("/covoitureur/{id}/solde")
    public double getSolde(@PathVariable Long id) {
        return covoiturageService.getSoldeByCovoitureurId(id);
    }



}
