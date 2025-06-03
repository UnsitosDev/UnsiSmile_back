package edu.mx.unsis.unsiSmile.model.patients;

import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import edu.mx.unsis.unsiSmile.model.utils.AuditModel;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "progress_notes")
public class ProgressNoteModel extends AuditModel {
    @Id
    @Column(name = "id_progress_note", nullable = false, updatable = false, length = 36)
    private String idProgressNote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_patient", nullable = false)
    private PatientModel patient;

    @Column(name = "blood_pressure", nullable = false, length = 10)
    private String bloodPressure;

    @Column(name = "temperature", nullable = false, precision = 5, scale = 2)
    private BigDecimal temperature;

    @Column(name = "heart_rate", nullable = false)
    private Integer heartRate;

    @Column(name = "respiratory_rate", nullable = false)
    private Integer respiratoryRate;

    @Column(name = "oxygen_saturation", nullable = false, precision = 5, scale = 2)
    private BigDecimal oxygenSaturation;

    @Column(name = "diagnosis", nullable = false, columnDefinition = "TEXT")
    private String diagnosis;

    @Column(name = "prognosis", nullable = false, columnDefinition = "TEXT")
    private String prognosis;

    @Column(name = "treatment", nullable = false, columnDefinition = "LONGTEXT")
    private String treatment;

    @Column(name = "indications", columnDefinition = "LONGTEXT")
    private String indications;
    
    @Column(name = "clinical_status", columnDefinition = "LONGTEXT")
    private String clinicalStatus;

    @ManyToOne
    @JoinColumn(name = "fk_professor", nullable = false)
    private ProfessorModel professor;

    @PrePersist
    public void generateId() {
        if (idProgressNote == null) {
            idProgressNote = UUID.randomUUID().toString();
        }
    }
}