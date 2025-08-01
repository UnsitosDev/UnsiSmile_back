package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth.ToothConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth.ToothConditionResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothConditionModel;

@Component
public class ToothConditionMapper implements BaseMapper<ToothConditionResponse, ToothConditionRequest, ToothConditionModel> {

    @Override
    public ToothConditionModel toEntity(ToothConditionRequest dto) {
        return ToothConditionModel.builder()
                .idToothCondition(dto.getIdToothCondition())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public ToothConditionResponse toDto(ToothConditionModel entity) {
        return ToothConditionResponse.builder()
                .idToothCondition(entity.getIdToothCondition())
                .description(entity.getDescription())
                .build();
    }

    @Override
    public List<ToothConditionResponse> toDtos(List<ToothConditionModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ToothConditionRequest request, ToothConditionModel entity) {
        entity.setDescription(request.getDescription());
    }
}
