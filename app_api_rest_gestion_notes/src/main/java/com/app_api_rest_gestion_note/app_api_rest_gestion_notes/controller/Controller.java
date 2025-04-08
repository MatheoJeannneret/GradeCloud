package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<List<HashMap<String, Object>>> getNotesByEleve(@RequestParam String username) {
        try {
            HashMap<Branche, ArrayList<Double>> notesMap = workerService.getNotesByEleve(username);
            List<HashMap<String, Object>> response = new ArrayList<>();

            for (Map.Entry<Branche, ArrayList<Double>> entry : notesMap.entrySet()) {
                HashMap<String, Object> brancheBlock = new HashMap<>();
                brancheBlock.put("branche", entry.getKey().getNom()); // nom de la branche
                brancheBlock.put("notes", entry.getValue()); // liste de notes
                response.add(brancheBlock);
            }

            return ResponseEntity.ok(response); // 200 OK avec les données
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 si élève non trouvé
        }
    }

    
      // 2. GET examens par classe
     
      @GetMapping("/getexamensbyclasse")
      public List<Examen> getExamensByClasse(@RequestParam String classNom) {
      return workerService.getExamensByClasse(classNom);
      }
     
    // 3. POST créer examen
    @PostMapping("/createexamen")
    public Examen createExamen(@RequestParam String nom, @RequestParam String description,
            @RequestParam LocalDateTime date, @RequestParam Integer brancheId, @RequestParam Integer classeId) {
        return workerService.createExamen(nom, description, date, brancheId, classeId);
    }

    // 4. DELETE supprimer examen
    @DeleteMapping("/deleteexamen")
    public void deleteExamen(@RequestParam Integer examenId) {
        workerService.deleteExamen(examenId);
    }

    // 5. PUT modifier examen
    @PutMapping("/updateexamen")
    public Examen updateExamen(@RequestParam Integer examenId, @RequestParam String nom,
            @RequestParam String description, @RequestParam LocalDateTime date, @RequestParam Integer brancheId,
            @RequestParam Integer classeId) {
        return workerService.updateExamen(examenId, nom, description, date, brancheId, classeId);
    }

    // 6. GET classe
    @GetMapping("/getclasse")
    public List<Classe> getClasses() {
        return workerService.getClasses();
    }

    // 7. GET Branches
    @GetMapping("/getbranche")
    public List<Branche> getBranches() {
        return workerService.getBranches();
    }

    // 8. Get Eleve by Classes
    @GetMapping("/getelevebyclasse")
    public List<Eleve> getEleveByClasse(@RequestParam String classNom) {
        return workerService.getElevesByClasse(classNom);
    }

    // 9. create note
    @PostMapping("/createnote")
    public Note createNote(@RequestParam String username, @RequestParam Integer examenId,
            @RequestParam Double noteChiffre) {
        return workerService.createNote(username, examenId, noteChiffre);
    }

}