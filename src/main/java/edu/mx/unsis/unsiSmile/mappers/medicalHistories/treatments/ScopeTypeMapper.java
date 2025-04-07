package edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.ScopeTypeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.ScopeTypeResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.ScopeTypeModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ScopeTypeMapper implements BaseMapper<ScopeTypeResponse, ScopeTypeRequest, ScopeTypeModel> {

    @Override
    public ScopeTypeModel toEntity(ScopeTypeRequest dto) {
        return ScopeTypeModel.builder()
                .idScopeType(dto.getIdScopeType())
                .name(dto.getName())
                .build();
    }

    @Override
    public ScopeTypeResponse toDto(ScopeTypeModel entity) {
        return ScopeTypeResponse.builder()
                .idScopeType(entity.getIdScopeType())
                .name(entity.getName())
                .build();
    }

    @Override
    public List<ScopeTypeResponse> toDtos(List<ScopeTypeModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ScopeTypeRequest request, ScopeTypeModel entity) {
        entity.setName(request.getName());
    }
}