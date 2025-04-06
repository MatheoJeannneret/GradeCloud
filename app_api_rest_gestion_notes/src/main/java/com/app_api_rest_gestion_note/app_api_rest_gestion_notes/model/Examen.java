package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model;

import jakarta.persistence.Column; 

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue; 

import jakarta.persistence.GenerationType; 

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table; 
import java.time.LocalDateTime;


@Entity
@Table(name = "t_examen")
public class Examen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_examen")
    private Integer id;

    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "nom", length = 100, nullable = false)
    private String nom;
   
    @ManyToOne
    @JoinColumn(name = "fk_branche", nullable = false)
    private Branche branche;
 
    @ManyToOne
    @JoinColumn(name = "fk_classe", nullable = false)
    private Classe classe;

    // Getters et Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Branche getBranche() {
        return branche;
    }

    public void setBranche(Branche branche) {
        this.branche = branche;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }
    
}