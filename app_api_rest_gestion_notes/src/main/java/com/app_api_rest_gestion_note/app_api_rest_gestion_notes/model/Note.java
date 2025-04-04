package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model;

import jakarta.persistence.*;


@Entity
@Table(name = "tr_eleve_examen")

public class Note {


    @ManyToOne
    @JoinColumn(name = "fk_examen", nullable = false)
    private Examen examen;

    @ManyToOne
    @JoinColumn(name = "fk_eleve", nullable = false)
    private Eleve eleve;

    @Column(name = "note")
    private Double note;

    // Getters and Setters
    public Examen getExamen() {
        return examen;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    public Eleve getEleve() {
        return eleve;
    }

    public void setEleve(Eleve eleve) {
        this.eleve = eleve;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }
}

