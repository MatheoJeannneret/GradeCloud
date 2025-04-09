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
    public HashMap<Branche, ArrayList<Double>> getNotesByEleve(String username) {

        Eleve eleve = eleveRepository.findByUsername(username);
        HashMap<Branche, ArrayList<Double>> notesParBranche = null;
        if (eleve != null) {
            List<Note> notes = noteRepository.findByEleveId(eleve.getId());
            if (notes != null) {
                notesParBranche = new HashMap<>();

                for (Note note : notes) {
                    Examen examen = note.getExamen();
                    Branche branche = examen.getBranche();
                    Double valeurNote = note.getNote();

                    if (!notesParBranche.containsKey(branche)) {
                        notesParBranche.put(branche, new ArrayList<>());
                    }
                    notesParBranche.get(branche).add(valeurNote);
                }
            }
        }

        return notesParBranche;
    }

    // 2. Récupérer les examens d'une classe
    public List<Examen> getExamensByClasse(String classNom) {
        Classe classe = classeRepository.findByNom(classNom);
        List<Examen> examens = null;
        if (classe != null) {
            examens = examenRepository.findByClasseId(classe.getId());
        }

        return examens;
    }

    // 3. Créer un examen
    public Examen createExamen(String nom, String description, LocalDateTime date, Integer brancheId,
            Integer classeId) {
        Examen examen = null;
        Branche branche = brancheRepository.findBrancheById(brancheId);

        Classe classe = classeRepository.findClasseById(classeId);
        if (branche != null && classe != null) {
            examen = new Examen(description, date, nom, branche, classe);
            return examenRepository.save(examen);
        } else {
            return examen;
        }
    }

    // 4. Supprimer un examen
    @Transactional
    public boolean deleteExamen(Integer examenId) {
        boolean response = false;
        if (examenRepository.existsById(examenId)) {
            examenRepository.deleteById(examenId);
            response = true;
        }

        return response;
    }

    // 5. Modifier un examen
    @Transactional
    public Examen updateExamen(Integer examenId, String nom, String description, LocalDateTime date, Integer brancheId,
            Integer classeId) {
        // Recherche de l'examen par ID
        Optional<Examen> optionalExamen = examenRepository.findById(examenId);

        // Si l'examen existe
        if (optionalExamen.isPresent()) {
            Examen examen = optionalExamen.get();

            // Recherche des entités Branche et Classe
            Branche branche = brancheRepository.findBrancheById(brancheId);
            Classe classe = classeRepository.findClasseById(classeId);

            // Si les entités Branche et Classe existent
            if (branche != null && classe != null) {
                // Mise à jour des champs de l'examen
                examen.setNom(nom);
                examen.setDescription(description);
                examen.setDate(date);
                examen.setBranche(branche);
                examen.setClasse(classe);

                // Sauvegarde de l'examen mis à jour
                return examenRepository.save(examen);
            } else {
                // Retourner null ou une exception si Branche ou Classe n'existent pas
                return null; // ou vous pouvez lancer une exception
            }
        }

        // Si l'examen n'existe pas, retourner null
        return null;
    }

    // 6. get Classes
    public List<Classe> getClasses() {
        return (List<Classe>) classeRepository.findAll();
    }

    // 7. get Branches
    public List<Branche> getBranches() {
        return (List<Branche>) brancheRepository.findAll();
    }

    // 8. get Eleves par Classe
    public List<Eleve> getElevesByClasse(String classNom) {
        Classe classe = classeRepository.findByNom(classNom);
        List<Eleve> eleves = null;
        if (classe != null) {
            eleves = eleveRepository.findByClasse(classe);
        }
        return eleves;

    }

    // 9. create Note

    public Note createNote(String username, Integer examenId, Double noteChiffre) {
        Eleve eleve = eleveRepository.findByUsername(username);
        Optional<Examen> optExam = examenRepository.findById(examenId);
        Note note = null;
        if (optExam.isPresent() && examenRepository.existsById(examenId)) {
            note = new Note(optExam.get(), eleve, noteChiffre);
            noteRepository.save(note);
        }

        return note;

    }

}
