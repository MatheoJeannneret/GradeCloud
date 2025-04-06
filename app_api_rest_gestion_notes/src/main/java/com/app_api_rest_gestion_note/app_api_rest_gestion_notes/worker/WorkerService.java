package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Branche;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.BrancheRepository;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Classe;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.ClasseRepository;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Eleve;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.EleveRepository;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Examen;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.ExamenRepository;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Note;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.NoteRepository;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class WorkerService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private ExamenRepository examenRepository;

    @Autowired
    private ClasseRepository classeRepository;

    @Autowired
    private EleveRepository eleveRepository;

    
    @Autowired
    private BrancheRepository brancheRepository;

    // 1. Récupérer les notes d'un élève triées par branche
    public HashMap<Branche, ArrayList<Double>> getNotesByEleve(String  username) {
   
        Eleve eleve = eleveRepository.findByUsername(username);
        List<Note> notes = noteRepository.findByEleveId(eleve.getId());
        HashMap<Branche, ArrayList<Double>> notesParBranche = new HashMap<>();
    
        for (Note note : notes) {
            Examen examen = note.getExamen();
            Branche branche = examen.getBranche();
            Double valeurNote = note.getNote();
    
            if (!notesParBranche.containsKey(branche)) {
                notesParBranche.put(branche, new ArrayList<>());
            }
            notesParBranche.get(branche).add(valeurNote);
        }
    
        return notesParBranche;
    }
    

    // 2. Récupérer les examens d'une classe
    public List<Examen> getExamensByClasse(String classNom) {
        Classe classe = classeRepository.findByNom(classNom);

        List<Examen> examens = examenRepository.findByClasseId(classe.getId());
        return examens;
    }

    // 3. Créer un examen
    public Examen createExamen(String nom,  String description,  LocalDateTime date,  Integer brancheId,  Integer classeId) {
        Branche branche = brancheRepository.findBrancheById(brancheId);
        Classe classe = classeRepository.findClasseById(classeId);
         
        Examen examen = new Examen(description, date, nom, branche, classe);



        return examenRepository.save(examen);
    }

    // 4. Supprimer un examen
    @Transactional
    public void deleteExamen(Integer examenId) {
        
        if (examenRepository.existsById(examenId)) {
            examenRepository.deleteById(examenId);
        } 
    }

    // 5. Modifier un examen
    public Examen updateExamen(Integer examenId, String nom, String description, LocalDateTime date, Integer brancheId, Integer classeId) {
        Optional<Examen> optionalExamen = examenRepository.findById(examenId);
    
        if (optionalExamen.isPresent()) {
            Examen examen = optionalExamen.get();
    
            Branche branche = brancheRepository.findBrancheById(brancheId);
            Classe classe = classeRepository.findClasseById(classeId);
    
            examen.setNom(nom);
            examen.setDescription(description);
            examen.setDate(date);
            examen.setBranche(branche);
            examen.setClasse(classe);
    
            return examenRepository.save(examen); // mise à jour propre
        }
    
        return null; // ou tu peux lancer une exception ici
    }
    //6. get Classes
    public List<Classe> getClasses(){
        return (List<Classe>) classeRepository.findAll();

    }

    //7. get Branches
    public List<Branche> getBranches(){
        return (List<Branche>) brancheRepository.findAll();

    }
    
}

