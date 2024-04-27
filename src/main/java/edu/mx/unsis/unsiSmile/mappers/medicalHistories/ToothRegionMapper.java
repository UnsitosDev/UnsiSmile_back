package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothRegionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothRegionResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ToothRegionModel;

@Component
public class ToothRegionMapper implements BaseMapper<ToothRegionResponse, ToothRegionRequest, ToothRegionModel> {

    @Override
    public ToothRegionModel toEntity(ToothRegionRequest dto) {
        return ToothRegionModel.builder()
                .idToothRegion(dto.getIdToothRegion())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public ToothRegionResponse toDto(ToothRegionModel entity) {
        return ToothRegionResponse.builder()
                .idToothRegion(entity.getIdToothRegion())
                .description(entity.getDescription())
                .build();
    }

    @Override
    public List<ToothRegionResponse> toDtos(List<ToothRegionModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ToothRegionRequest request, ToothRegionModel entity) {
        entity.setDescription(request.getDescription());
    }
}
