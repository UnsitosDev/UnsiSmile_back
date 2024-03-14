package edu.mx.unsis.unsiSmile.model;

import jakarta.persistence.*;

@Entity
public class Cycle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCycle;

    private String cycleName;

    private boolean status = true;

    // Constructors, getters and setters

    public Cycle() {
    }

    public Cycle(String cycleName) {
        this.cycleName = cycleName;
    }

    public Long getIdCycle() {
        return idCycle;
    }

    public void setIdCycle(Long idCycle) {
        this.idCycle = idCycle;
    }

    public String getCycleName() {
        return cycleName;
    }

    public void setCycleName(String cycleName) {
        this.cycleName = cycleName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

