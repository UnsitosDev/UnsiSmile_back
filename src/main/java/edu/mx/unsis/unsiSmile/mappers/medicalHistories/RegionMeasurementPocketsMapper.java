package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.RegionMeasurementPocketsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.RegionMeasurementPocketsResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.RegionMeasurementPocketsModel;

@Component
public class RegionMeasurementPocketsMapper implements BaseMapper<RegionMeasurementPocketsResponse, RegionMeasurementPocketsRequest, RegionMeasurementPocketsModel> {

    @Override
    public RegionMeasurementPocketsModel toEntity(RegionMeasurementPocketsRequest dto) {
        return RegionMeasurementPocketsModel.builder()
                .idRegionsMeasurementPockets(dto.getIdRegionsMeasurementPockets())
                .region(dto.getRegion())
                .build();
    }

    @Override
    public RegionMeasurementPocketsResponse toDto(RegionMeasurementPocketsModel entity) {
        return RegionMeasurementPocketsResponse.builder()
                .idRegionsMeasurementPockets(entity.getIdRegionsMeasurementPockets())
                .region(entity.getRegion())
                .build();
    }

    @Override
    public List<RegionMeasurementPocketsResponse> toDtos(List<RegionMeasurementPocketsModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(RegionMeasurementPocketsRequest request, RegionMeasurementPocketsModel entity) {
        // Implementa la lógica para actualizar la entidad según los datos de la solicitud
    }
}
