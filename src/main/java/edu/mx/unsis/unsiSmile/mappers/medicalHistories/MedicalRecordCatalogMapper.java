package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ClinicalHistoryCatalogRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ClinicalHistoryCatalogResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.MedicalRecordCatalogModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicalRecordCatalogMapper implements BaseMapper<ClinicalHistoryCatalogResponse, ClinicalHistoryCatalogRequest, MedicalRecordCatalogModel> {

    @Override
    public MedicalRecordCatalogModel toEntity(ClinicalHistoryCatalogRequest dto) {
        return MedicalRecordCatalogModel.builder()
                .medicalRecordName(dto.getClinicalHistoryName())
                .build();
    }

    @Override
    public ClinicalHistoryCatalogResponse toDto(MedicalRecordCatalogModel entity) {
        return ClinicalHistoryCatalogResponse.builder()
                .idClinicalHistoryCatalog(entity.getIdMedicalRecordCatalog())
                .clinicalHistoryName(entity.getMedicalRecordName())
                .medicalRecordNumber(null)
                .appointmentDate(null)
                .formSections(Collections.emptyList())
                .build();
    }

    @Override
    public List<ClinicalHistoryCatalogResponse> toDtos(List<MedicalRecordCatalogModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ClinicalHistoryCatalogRequest request, MedicalRecordCatalogModel entity) {}
}
