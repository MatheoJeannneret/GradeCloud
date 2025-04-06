package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Branche;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Classe;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Eleve;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Examen;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.worker.WorkerService;

import java.time.LocalDateTime;
import java.util.*;

@RestController
//@RequestMapping("/gestionnotes")
public class Controller {
    
     
   

    @Autowired
    private WorkerService workerService;

    // 1. GET notes par élève, chemin simple
    @GetMapping("/getnotesbyeleve")
    public List<HashMap<String, Object>> getNotesByEleve(@RequestParam String username) {
        
        HashMap<Branche, ArrayList<Double>> notesMap = workerService.getNotesByEleve(username);

        // On formate les données en JSON lisible : [{ "branche": "MATH", "notes": [15.5, 14.0] }, ...]
        List<HashMap<String, Object>> response = new ArrayList<>();

        for (HashMap.Entry<Branche, ArrayList<Double>> entry : notesMap.entrySet()) {
            HashMap<String, Object> brancheBlock = new HashMap<>();
            brancheBlock.put("branche", entry.getKey().getNom());
            brancheBlock.put("notes", entry.getValue());
            response.add(brancheBlock);
        }

        return response;
    }

    // 2. GET examens par classe
    @GetMapping("/getexamensbyclasse")
    public List<Examen> getExamensByClasse(@RequestParam String classNom) {
        return workerService.getExamensByClasse(classNom);
    }

    // 3. POST créer examen
    @PostMapping("/createexamen")
    public Examen createExamen(@RequestParam String nom, @RequestParam String description, @RequestParam LocalDateTime date, @RequestParam Integer brancheId, @RequestParam Integer classeId) {
        return workerService.createExamen(nom, description, date, brancheId, classeId);
    }

    // 4. DELETE supprimer examen
    @DeleteMapping("/deleteexamen")
    public void deleteExamen(@RequestParam Integer examenId) {
        workerService.deleteExamen(examenId);
    }

    // 5. PUT modifier examen
    @PutMapping("/updateexamen")
    public Examen updateExamen(@RequestParam Integer examenId, @RequestParam String nom, @RequestParam String description, @RequestParam LocalDateTime date, @RequestParam Integer brancheId, @RequestParam Integer classeId) {
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
}