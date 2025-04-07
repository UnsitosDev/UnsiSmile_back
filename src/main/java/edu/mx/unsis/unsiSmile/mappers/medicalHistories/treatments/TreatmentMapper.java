package edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.*;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.ScopeTypeModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TreatmentMapper implements BaseMapper<TreatmentResponse, TreatmentRequest, TreatmentModel> {
    private final ScopeTypeMapper scopeTypeMapper;

    @Override
    public TreatmentModel toEntity(TreatmentRequest dto) {
        return TreatmentModel.builder()
                .idTreatment(dto.getIdTreatment())
                .name(dto.getName())
                .scopeType(ScopeTypeModel.builder()
                        .idScopeType(dto.getScopeTypeId())
                        .build())
                .cost(dto.getCost() != null ? dto.getCost() : BigDecimal.ZERO)
                .clinicalHistoryCatalog(ClinicalHistoryCatalogModel.builder()
                                .idClinicalHistoryCatalog(dto.getClinicalHistoryCatalogId())
                                .build())
                .build();
    }

    @Override
    public TreatmentResponse toDto(TreatmentModel entity) {
        return TreatmentResponse.builder()
                .idTreatment(entity.getIdTreatment())
                .name(entity.getName())
                .scopeType(scopeTypeMapper.toDto(entity.getScopeType()))
                .cost(entity.getCost())
                .clinicalHistoryCatalogId(entity.getClinicalHistoryCatalog() != null ?
                        entity.getClinicalHistoryCatalog().getIdClinicalHistoryCatalog() : null)
                .clinicalHistoryCatalogName(entity.getClinicalHistoryCatalog() != null ?
                        entity.getClinicalHistoryCatalog().getClinicalHistoryName() : null)
                .build();
    }

    @Override
    public List<TreatmentResponse> toDtos(List<TreatmentModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(TreatmentRequest request, TreatmentModel entity) {
        entity.setName(request.getName());
        entity.setScopeType(ScopeTypeModel.builder()
                .idScopeType(request.getScopeTypeId())
                .build());
        entity.setCost(request.getCost() != null ? request.getCost() : entity.getCost());
        entity.setClinicalHistoryCatalog(
                ClinicalHistoryCatalogModel.builder()
                        .idClinicalHistoryCatalog(request.getClinicalHistoryCatalogId())
                        .build());
    }
}