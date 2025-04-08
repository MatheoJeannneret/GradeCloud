package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface EleveRepository extends CrudRepository<Eleve, Integer>{    
    Eleve findByUsername(String username);
    List<Eleve> findByClasse(Classe classe);
}
