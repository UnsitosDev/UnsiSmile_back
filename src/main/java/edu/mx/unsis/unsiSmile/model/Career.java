package edu.mx.unsis.unsiSmile.model;

import jakarta.persistence.*;
@Entity
public class Career {

    @Id
    private String idCareer;

    private String career;

    // Constructores, getters y setters

    public Career() {
    }

    public Career(String idCareer, String career) {
        this.idCareer = idCareer;
        this.career = career;
    }

    public String getIdCareer() {
        return idCareer;
    }

    public void setIdCareer(String idCareer) {
        this.idCareer = idCareer;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }
}

