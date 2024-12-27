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

    private Long idClinicalHistoryCatalog;

    private String clinicalHistoryName;

    private Long fileNumber;

    private LocalDateTime date;

    List<FormSectionResponse> formSections;
}
