package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model;
import org.springframework.data.repository.CrudRepository;
import java.util.*;


public interface BrancheRepository extends CrudRepository<Branche, Integer>{    
    Branche findBrancheById(Integer brancheId);
}
