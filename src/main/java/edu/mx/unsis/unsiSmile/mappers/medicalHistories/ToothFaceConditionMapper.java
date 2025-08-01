package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.teeth.ToothFaceConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothFaceConditionResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothFaceConditionModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ToothFaceConditionMapper implements BaseMapper<ToothFaceConditionResponse, ToothFaceConditionRequest, ToothFaceConditionModel> {

    @Override
    public ToothFaceConditionModel toEntity(ToothFaceConditionRequest dto) {
        return ToothFaceConditionModel.builder()
                .idToothFaceCondition(dto.getIdToothFaceCondition())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public ToothFaceConditionResponse toDto(ToothFaceConditionModel entity) {
        return ToothFaceConditionResponse.builder()
                .idToothFaceCondition(entity.getIdToothFaceCondition())
                .description(entity.getDescription())
                .build();
    }

    @Override
    public List<ToothFaceConditionResponse> toDtos(List<ToothFaceConditionModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ToothFaceConditionRequest request, ToothFaceConditionModel entity) {
        entity.setDescription(request.getDescription());
    }
}
