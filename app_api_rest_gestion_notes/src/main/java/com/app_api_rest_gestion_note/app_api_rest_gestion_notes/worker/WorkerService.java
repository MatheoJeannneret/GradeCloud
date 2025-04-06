package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Branche;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.ClasseRepository;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.EleveRepository;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Examen;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.ExamenRepository;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.Note;
import com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model.NoteRepository;

import jakarta.transaction.Transactional;

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

    // 1. Récupérer les notes d'un élève triées par branche
    public HashMap<Branche, ArrayList<Double>> getNotesByEleve(Integer eleveId) {
        List<Note> notes = noteRepository.findByEleveId(eleveId);
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
    public List<Examen> getExamensByClasse(Integer classeId) {
        return examenRepository.findByClasseId(classeId);
    }

    // 3. Créer un examen
    public Examen createExamen(Examen examen) {
        return examenRepository.save(examen);
    }

    // 4. Supprimer un examen
    @Transactional
    public void deleteExamen(Integer examenId) {
        Examen examen = examenRepository.findById(examenId).orElse(null);
        if (examen != null) {
            examenRepository.delete(examen);
        } else {
            System.out.println("Examen avec ID " + examenId + " introuvable.");
        }
    }

    // 5. Modifier un examen
    public Examen updateExamen(Integer examenId, Examen updatedExamen) {
        Examen examen = examenRepository.findById(examenId).orElse(null);

        if (examen != null) {
            examen.setNom(updatedExamen.getNom());
            examen.setDescription(updatedExamen.getDescription());
            examen.setDate(updatedExamen.getDate());
            examen.setBranche(updatedExamen.getBranche());
            examen.setClasse(updatedExamen.getClasse());
            examen =  examenRepository.save(examen);
        } 
        return examen;

    }
    
}

