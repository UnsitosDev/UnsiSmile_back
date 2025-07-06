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
@Table(name = "medical_record_digitizers")
public class MedicalRecordDigitizerModel extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medical_record_digitizer", nullable = false)
    private Long idMedicalRecordDigitizer;

    @ManyToOne
    @JoinColumn(name = "fk_student", nullable = false)
    private StudentModel student;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;
}