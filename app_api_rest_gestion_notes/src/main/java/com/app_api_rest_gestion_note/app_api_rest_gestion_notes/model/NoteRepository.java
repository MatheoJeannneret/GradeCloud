package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model;
import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface NoteRepository extends CrudRepository<Note, Integer>{
    List<Note> findByEleveId(Integer eleveId);
}
