package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model;

import jakarta.persistence.*;

@Entity
@Table(name = "t_branche")
public class Branche {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_branche")
    private Integer id;

    @Column(name = "nom", length = 200, nullable = false)
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

