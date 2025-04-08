package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface ExamenRepository extends CrudRepository<Examen, Integer>{   
    List<Examen> findByClasseId(Integer id);
    
}
