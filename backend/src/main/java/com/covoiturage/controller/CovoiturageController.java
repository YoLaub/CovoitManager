package com.covoiturage.controller;

import com.covoiturage.model.Covoitureur;
import com.covoiturage.model.Presence;
import com.covoiturage.model.Trajet;
import com.covoiturage.service.CovoiturageService;
import com.covoiturage.service.SendMail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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


    // Remettre tous les soldes à zéro
    @PutMapping("/covoitureur/reset-soldes")
    public String resetSoldes() {
        covoiturageService.resetAllSoldes();
        return "Tous les soldes ont été remis à zéro ✅";
    }

    // Remettre tous les soldes à zéro
    @PutMapping("/covoitureur/reset-solde")
    public ResponseEntity<?> resetSolde(@RequestBody Map<String,Long> body) {
       Long id = body.get("id");
       covoiturageService.resetSolde(id);
       return ResponseEntity.ok("Solde réinitialisé pour le covoitureur " + id);
    }


    @GetMapping("/covoitureur/{id}/solde")
    public double getSolde(@PathVariable Long id) {
        return covoiturageService.getSoldeByCovoitureurId(id);
    }


    @PostMapping("/send")
    public ResponseEntity<?> sendEmail(@RequestBody Map<String,String> body) throws IOException {
        String email = body.get("email");
        String message = body.get("message");
        try {
            SendMail.sendEmail(email, message);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Email envoyé à " + email
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "error", e.getMessage()
            ));
        }
    }


}
