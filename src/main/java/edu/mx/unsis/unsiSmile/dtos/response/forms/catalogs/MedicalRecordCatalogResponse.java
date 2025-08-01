package edu.mx.unsis.unsiSmile.dtos.response.forms.catalogs;

import edu.mx.unsis.unsiSmile.dtos.response.forms.sections.FormSectionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MedicalRecordCatalogResponse {
    private Long idPatientMedicalRecord;
    private Long idMedicalRecordCatalog;
    private String medicalRecordName;
    private Long medicalRecordNumber;
    private LocalDateTime appointmentDate;
    List<FormSectionResponse> formSections;
}