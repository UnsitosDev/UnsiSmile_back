package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PatientMedicalRecordRes {
    private Long idPatientMedicalRecord;
    private MedicalRecordCatalogResponse medicalRecordCatalog;
    private LocalDate appointmentDate;
}