package edu.mx.unsis.unsiSmile.model.medicalHistories;

import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "medical_histories")
public class MedicalHistoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medical_history")
    private Long idMedicalHistory;

    @ManyToOne
    @JoinColumn(name = "fk_patient", referencedColumnName = "id_patient")
    private PatientModel patient;

    @ManyToOne
    @JoinColumn(name = "fk_facial_exam", referencedColumnName = "id_facial_exam")
    private FacialExamModel facialExam;

    @ManyToOne
    @JoinColumn(name = "fk_non_pathological_personal_antecedents", referencedColumnName = "id_non_pathological_personal_antecedents")
    private NonPathologicalPersonalAntecedentsModel nonPathologicalPersonalAntecedents;

    @ManyToOne
    @JoinColumn(name = "fk_initial_odontogram", referencedColumnName = "id_odontogram")
    private OdontogramModel initialOdontogram;

    @ManyToOne
    @JoinColumn(name = "fk_final_odontogram", referencedColumnName = "id_odontogram")
    private OdontogramModel finalOdontogram;
}
