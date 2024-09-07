package edu.mx.unsis.unsiSmile.model;

import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "patient_clinical_histories")
public class PatientClinicalHistoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patient_clinical_history")
    private Integer idPatientClinicalHistory;

    @ManyToOne
    @JoinColumn(name = "fk_clinical_history_catalog")
    private ClinicalHistoryCatalogModel clinicalHistoryCatalog;

    @ManyToOne
    @JoinColumn(name = "fk_patient")
    private PatientModel patient;

    @Column(name = "date")
    private LocalDateTime date;
}
