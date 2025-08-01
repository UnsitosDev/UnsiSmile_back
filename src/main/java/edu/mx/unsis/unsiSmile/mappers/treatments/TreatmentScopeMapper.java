package edu.mx.unsis.unsiSmile.mappers.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentScopeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.treatments.TreatmentScopeResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentScopeModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TreatmentScopeMapper implements BaseMapper<TreatmentScopeResponse, TreatmentScopeRequest, TreatmentScopeModel> {

    @Override
    public TreatmentScopeModel toEntity(TreatmentScopeRequest dto) {
        return TreatmentScopeModel.builder()
                .idTreatmentScope(dto.getIdScopeTreatment())
                .name(dto.getName())
                .build();
    }

    @Override
    public TreatmentScopeResponse toDto(TreatmentScopeModel entity) {
        return TreatmentScopeResponse.builder()
                .idScopeTreatment(entity.getIdTreatmentScope())
                .name(entity.getName())
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
        entity.setName(request.getName());
    }
}