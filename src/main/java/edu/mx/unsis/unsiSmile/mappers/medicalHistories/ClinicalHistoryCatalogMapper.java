package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ClinicalHistoryCatalogRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ClinicalHistoryCatalogResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.ClinicalHistoryCatalogModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClinicalHistoryCatalogMapper implements BaseMapper<ClinicalHistoryCatalogResponse, ClinicalHistoryCatalogRequest, ClinicalHistoryCatalogModel> {

    @Override
    public ClinicalHistoryCatalogModel toEntity(ClinicalHistoryCatalogRequest dto) {
        return ClinicalHistoryCatalogModel.builder()
                .clinicalHistoryName(dto.getClinicalHistoryName())
                .build();
    }

    @Override
    public ClinicalHistoryCatalogResponse toDto(ClinicalHistoryCatalogModel entity) {
        return ClinicalHistoryCatalogResponse.builder()
                .idClinicalHistoryCatalog(entity.getIdClinicalHistoryCatalog())
                .clinicalHistoryName(entity.getClinicalHistoryName())
                .medicalRecordNumber(null)
                .appointmentDate(null)
                .formSections(Collections.emptyList())
                .build();
    }

    @Override
    public List<ClinicalHistoryCatalogResponse> toDtos(List<ClinicalHistoryCatalogModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ClinicalHistoryCatalogRequest request, ClinicalHistoryCatalogModel entity) {}
}
