package edu.mx.unsis.unsiSmile.mappers.patients;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.patients.OccupationRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.OccupationResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.patients.demographics.OccupationModel;

@Component
public class OccupationMapper implements BaseMapper<OccupationResponse, OccupationRequest, OccupationModel> {

    @Override
    public OccupationModel toEntity(OccupationRequest dto) {
        return OccupationModel.builder()
                .idOccupation(dto.getIdOccupation())
                .occupation(dto.getOccupation())
                .build();
    }

    @Override
    public OccupationResponse toDto(OccupationModel entity) {
        return OccupationResponse.builder()
                .idOccupation(entity.getIdOccupation())
                .occupation(entity.getOccupation())
                .build();
    }

    @Override
    public List<OccupationResponse> toDtos(List<OccupationModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(OccupationRequest request, OccupationModel entity) {
        entity.setOccupation(request.getOccupation());
    }
}