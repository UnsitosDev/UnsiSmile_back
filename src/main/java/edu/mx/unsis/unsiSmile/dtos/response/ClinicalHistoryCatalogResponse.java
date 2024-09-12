package edu.mx.unsis.unsiSmile.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClinicalHistoryCatalogResponse {

    private Long idClinicalHistoryCatalog;

    private String clinicalHistoryName;

    List<FormSectionResponse> formSections;
}
