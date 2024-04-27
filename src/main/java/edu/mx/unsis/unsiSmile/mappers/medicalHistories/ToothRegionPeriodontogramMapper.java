package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothRegionPeriodontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothRegionPeriodontogramResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ToothRegionPeriodontogramModel;

@Component
public class ToothRegionPeriodontogramMapper implements BaseMapper<ToothRegionPeriodontogramResponse, ToothRegionPeriodontogramRequest, ToothRegionPeriodontogramModel> {

    @Override
    public ToothRegionPeriodontogramModel toEntity(ToothRegionPeriodontogramRequest dto) {
        return ToothRegionPeriodontogramModel.builder()
                .idToothRegionsPeriodontogram(dto.getIdToothRegionsPeriodontogram())
                .region(dto.getRegion())
                .build();
    }

    @Override
    public ToothRegionPeriodontogramResponse toDto(ToothRegionPeriodontogramModel entity) {
        return ToothRegionPeriodontogramResponse.builder()
                .idToothRegionsPeriodontogram(entity.getIdToothRegionsPeriodontogram())
                .region(entity.getRegion())
                .build();
    }

    @Override
    public List<ToothRegionPeriodontogramResponse> toDtos(List<ToothRegionPeriodontogramModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ToothRegionPeriodontogramRequest request, ToothRegionPeriodontogramModel entity) {
        // Implementa la lógica para actualizar la entidad según los datos de la solicitud
    }
}
