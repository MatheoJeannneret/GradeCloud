package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model;

import jakarta.persistence.*;

@Entity
@Table(name = "t_classe")
public class Classe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_classe")
    private Integer id;

    @Column(name = "nom", length = 10, nullable = false, unique = true)
    private String nom;

    // Getters et Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}