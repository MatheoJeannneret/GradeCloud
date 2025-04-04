package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Branche;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Examen;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.worker.WorkerService;

import java.util.*;

@RestController
//@RequestMapping("/gestionnotes")
public class Controller {
     // Handler pour GET 

     @GetMapping("/getExample") 

     public String getExample(@RequestParam(value = "name", defaultValue = "World") String name) { 
     
     return String.format("Hello, %s!", name); 
     
     } 
     
     // Handler pour POST 
     
     @PostMapping("/postExample") 
     
     public String postExample(@RequestBody String body) { 
     
    return "Received POST request with body: " + body; 
     
     } 
     
     // Handler pour PUT 
     
     @PutMapping("/putExample") 
     
        public String putExample(@RequestBody String body) { 
     
     return "Received PUT request with body: " + body; 
     
     } 
/* 
    @Autowired
    private WorkerService workerService;

    // 1. GET notes par élève, chemin simple
    @GetMapping("/getnotesbyeleve")
    public List<HashMap<String, Object>> getNotesByEleve(@RequestParam Integer eleveId) {
        HashMap<Branche, ArrayList<Double>> notesMap = workerService.getNotesByEleve(eleveId);

        // On formate les données en JSON lisible : [{ "branche": "MATH", "notes": [15.5, 14.0] }, ...]
        List<HashMap<String, Object>> response = new ArrayList<>();

        for (HashMap.Entry<Branche, ArrayList<Double>> entry : notesMap.entrySet()) {
            HashMap<String, Object> brancheBlock = new HashMap<>();
            brancheBlock.put("branche", entry.getKey().toString());
            brancheBlock.put("notes", entry.getValue());
            response.add(brancheBlock);
        }

        return response;
    }

    // 2. GET examens par classe
    @GetMapping("/getexamensbyclasse")
    public List<Examen> getExamensByClasse(@RequestParam Integer classeId) {
        return workerService.getExamensByClasse(classeId);
    }

    // 3. POST créer examen
    @PostMapping("/createexamen")
    public Examen createExamen(@RequestBody Examen examen) {
        return workerService.createExamen(examen);
    }

    // 4. DELETE supprimer examen
    @DeleteMapping("/deleteexamen")
    public void deleteExamen(@RequestParam Integer examenId) {
        workerService.deleteExamen(examenId);
    }

    // 5. PUT modifier examen
    @PutMapping("/updateexamen")
    public Examen updateExamen(@RequestParam Integer examenId, @RequestBody Examen updatedExamen) {
        return workerService.updateExamen(examenId, updatedExamen);
    }*/
}
