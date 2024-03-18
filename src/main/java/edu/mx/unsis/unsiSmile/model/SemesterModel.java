package edu.mx.unsis.unsiSmile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "semesters")
public class SemesterModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_semester")
    private Long idSemester;

    @ManyToOne
    @JoinColumn(name = "fk_group")
    private GroupModel group;

    @ManyToOne
    @JoinColumn(name = "fk_cycle")
    private CycleModel cycle;

}
