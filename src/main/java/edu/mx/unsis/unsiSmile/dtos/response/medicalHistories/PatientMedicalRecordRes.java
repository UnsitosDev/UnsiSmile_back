package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PatientMedicalRecordRes {
    private Long idPatientClinicalHistory;
    private ClinicalHistoryCatalogResponse clinicalHistoryCatalog;
    private LocalDate appointmentDate;
}