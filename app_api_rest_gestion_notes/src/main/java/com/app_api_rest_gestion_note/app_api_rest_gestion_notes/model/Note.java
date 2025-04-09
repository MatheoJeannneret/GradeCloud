package com.app_api_rest_gestion_note.app_api_rest_gestion_notes.model;
import jakarta.persistence.*;

@Entity
@Table(name = "tr_eleve_examen")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_note")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_examen", nullable = false)
    private Examen examen;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_eleve", nullable = false)
    private Eleve eleve;

    @Column(name = "note")
    private Double note;
    
    public Note(){
    }

    public Note(Examen examen, Eleve eleve, Double note){
        this.examen = examen;
        this.eleve = eleve;
        this.note = note;
    }

    // Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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