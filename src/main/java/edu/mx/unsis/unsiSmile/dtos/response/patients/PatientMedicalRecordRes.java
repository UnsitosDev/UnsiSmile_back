package edu.mx.unsis.unsiSmile.dtos.response.patients;

import edu.mx.unsis.unsiSmile.dtos.response.forms.catalogs.MedicalRecordCatalogResponse;
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