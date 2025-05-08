package edu.mx.unsis.unsiSmile.dtos.response.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.response.FormSectionResponse;
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
public class ClinicalHistoryCatalogResponse {
    private Long idPatientMedicalRecord;

    private Long idClinicalHistoryCatalog;

    private String clinicalHistoryName;

    private Long medicalRecordNumber;

    private LocalDateTime appointmentDate;

    List<FormSectionResponse> formSections;
}
