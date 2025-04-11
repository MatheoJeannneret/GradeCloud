package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Branche;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Classe;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Eleve;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Examen;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Note;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.worker.WorkerService;

import java.time.LocalDateTime;
import java.util.*;

@RestController
// @RequestMapping("/gestionnotes")
public class Controller {

    @Autowired
    private WorkerService workerService;

    // 1. GET notes par élève, chemin simple
    @GetMapping("/getnotesbyeleve")
    public ResponseEntity<?> getNotesByEleve(@RequestParam String username) {
        HashMap<Branche, ArrayList<Double>> notesMap = workerService.getNotesByEleve(username);
        if (notesMap != null) {
            List<HashMap<String, Object>> response = new ArrayList<>();

            for (Map.Entry<Branche, ArrayList<Double>> entry : notesMap.entrySet()) {
                HashMap<String, Object> brancheBlock = new HashMap<>();
                brancheBlock.put("branche", entry.getKey().getNom()); // nom de la branche
                brancheBlock.put("notes", entry.getValue()); // liste de notes
                response.add(brancheBlock);
            }

            return ResponseEntity.ok(response); // 200 OK avec les données
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(List.of(new HashMap<>(Map.of("error", "Élève non trouvé"))));// 404 si élève ou note non
                                                                                       // trouvé
        }

    }

    // 2. GET examens par classe

    @GetMapping("/getexamensbyclasse")
    public ResponseEntity<?> getExamensByClasse(@RequestParam String classNom) {
        List<Examen> examens = workerService.getExamensByClasse(classNom);

        if (examens != null && !examens.isEmpty()) {
            return ResponseEntity.ok(examens);
        } else {
            Map<String, String> errorResponse = Map.of("error", "Aucun examen trouvé pour la classe : " + classNom);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    // 3. POST créer examen
    @PostMapping("/createexamen")
    public ResponseEntity<?> createExamen(
            @RequestParam String nom,
            @RequestParam String description,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date,
            @RequestParam Integer brancheId,
            @RequestParam Integer classeId) {

        Examen examen = workerService.createExamen(nom, description, date, brancheId, classeId);

        if (examen != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(examen); // 201 Created
        } else {
            Map<String, String> errorResponse = Map.of("error", "Branche ou classe introuvable.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse); // 400 Bad Request
        }
    }

    // 4. DELETE supprimer examen
    @DeleteMapping("/deleteexamen")
    public ResponseEntity<?> deleteExamen(@RequestParam Integer examenId) {
        boolean response = workerService.deleteExamen(examenId);
        if (response == true) {

            return ResponseEntity.ok().build();
        } else {
            Map<String, String> error = Map.of("error", "Examen non trouvé");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error); // 404 Not Found avec message
        }
    }

    @PutMapping("/updateexamen")
    public ResponseEntity<?> updateExamen(
            @RequestParam Integer examenId,
            @RequestParam String nom,
            @RequestParam String description,
            @RequestParam LocalDateTime date,
            @RequestParam Integer brancheId,
            @RequestParam Integer classeId) {

        Examen updatedExamen = workerService.updateExamen(examenId, nom, description, date, brancheId, classeId);

        if (updatedExamen != null) {
            // Si l'examen existe et a été mis à jour, on retourne le nouvel examen
            return ResponseEntity.ok(updatedExamen);
        } else {
            // Si l'examen n'a pas été trouvé
            Map<String, String> error = Map.of("error", "Examen, classe ou branche non trouvé");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    // 6. GET classe
    @GetMapping("/getclasse")
    public ResponseEntity<?> getClasses() {
        List<Classe> classes = workerService.getClasses();

        if (classes == null || classes.isEmpty()) {
            // Retourne une réponse 404 Not Found si la liste est vide ou null
            Map<String, String> error = Map.of("error", "Classe non trouvé");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        // Si des classes sont trouvées, retourne un 200 OK avec la liste des classes
        return ResponseEntity.ok(classes);
    }

    // 7. GET Branches
    @GetMapping("/getbranche")
    public ResponseEntity<?> getBranches() {
        List<Branche> branches = workerService.getBranches();
        if (branches == null || branches.isEmpty()) {
            // Retourne une réponse 404 Not Found si la liste est vide ou null
            Map<String, String> error = Map.of("error", "Bracnhe non trouvé");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        // Si des classes sont trouvées, retourne un 200 OK avec la liste des branches
        return ResponseEntity.ok(branches);

    }

    // 8. Get Eleve by Classes
    @GetMapping("/getelevebyclasse")
    public ResponseEntity<?> getEleveByClasse(@RequestParam String classNom) {
        List<Eleve> eleves = workerService.getElevesByClasse(classNom);

        // Si des élèves ont été trouvés pour cette classe
        if (eleves != null && !eleves.isEmpty()) {
            return ResponseEntity.ok(eleves); // Retourne une réponse 200 OK avec la liste des élèves
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Aucun élève trouvé pour la classe : " + classNom); // Retourne 404 si aucun élève n'a été trouvé

        }
    }

    // 9. create note
    @PostMapping("/createnote")
    public ResponseEntity<?> createNote(@RequestParam String username,
            @RequestParam Integer examenId,
            @RequestParam Double noteChiffre) {
        Note note = workerService.createNote(username, examenId, noteChiffre);

        // Vérifie si la note a été correctement créée
        if (note != null) {
            return ResponseEntity.ok(note); // Retourne 201 Created avec la note créée
        } else {
            // Si la note n'a pas pu être créée (élève ou examen introuvable)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Échec de la création de la note : Élève ou examen non trouvé.");
        }
    }

    //10. getExamenByEleve
    @GetMapping("/getexamenbyeleve")
    public ResponseEntity<?> getExamenByEleve(@RequestParam String username){
        List<Examen> examens = workerService.getExamenByEleve(username);
        if (examens != null && !examens.isEmpty()) {
            return ResponseEntity.ok(examens); // Retourne une réponse 200 OK avec la liste des élèves
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cet eleve n'existe pas ou n'a pas d'examen"); // Retourne 404 si aucun exmane n'a été
                                                                              // trouvé
        }
    }

}