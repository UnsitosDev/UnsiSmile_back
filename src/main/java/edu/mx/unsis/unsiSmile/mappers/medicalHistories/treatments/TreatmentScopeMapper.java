package edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentScopeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentScopeResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.ScopeTypeModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentScopeModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class TreatmentScopeMapper implements BaseMapper<TreatmentScopeResponse, TreatmentScopeRequest, TreatmentScopeModel> {
    private final ScopeTypeMapper scopeTypeMapper;

    @Override
    public TreatmentScopeModel toEntity(TreatmentScopeRequest dto) {
        return TreatmentScopeModel.builder()
                .idScope(dto.getIdScope())
                .scopeType(ScopeTypeModel.builder()
                        .idScopeType(dto.getScopeTypeId())
                        .build())
                .scopeName(dto.getScopeName())
                .build();
    }

    @Override
    public TreatmentScopeResponse toDto(TreatmentScopeModel entity) {
        return TreatmentScopeResponse.builder()
                .idScope(entity.getIdScope())
                .scopeName(entity.getScopeName())
                .scopeType(scopeTypeMapper.toDto(entity.getScopeType()))
                .build();
    }

    @Override
    public List<TreatmentScopeResponse> toDtos(List<TreatmentScopeModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(TreatmentScopeRequest request, TreatmentScopeModel entity) {
        entity.setScopeType(ScopeTypeModel.builder()
                .idScopeType(request.getScopeTypeId())
                .build());
        entity.setScopeName(request.getScopeName());
    }
}