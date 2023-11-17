package edu.mx.unsis.unsiSmile.model;

import jakarta.persistence.*;

public class SemesterGruop {
    @Id
    @ManyToOne
    @JoinColumn(name = "id_group")
    private Group group;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_semester")
    private Semester semester;
}
