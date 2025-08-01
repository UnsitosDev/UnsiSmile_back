package edu.mx.unsis.unsiSmile.model.students;

import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "semesters")
public class SemesterModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_semester")
    private Long idSemester;

    @Column(name = "semester_name", nullable = false, unique = true)
    private String semesterName;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate startDate;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "fk_cycle")
    private CycleModel cycle;
}