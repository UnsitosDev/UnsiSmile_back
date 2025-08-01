package edu.mx.unsis.unsiSmile.model.students;

import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "student_patient")
public class StudentPatientModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_student_patient")
    private Long idStudentPatient;

    @ManyToOne
    @JoinColumn(name = "fk_patient")
    private PatientModel patient;

    @ManyToOne
    @JoinColumn(name = "fk_student")
    private StudentModel student;
}