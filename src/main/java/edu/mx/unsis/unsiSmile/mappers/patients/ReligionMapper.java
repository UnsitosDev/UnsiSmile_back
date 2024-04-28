package edu.mx.unsis.unsiSmile.mappers.patients;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.patients.ReligionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.ReligionResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.ReligionModel;

@Component
public class ReligionMapper implements BaseMapper<ReligionResponse, ReligionRequest, ReligionModel> {

    @Override
    public ReligionModel toEntity(ReligionRequest dto) {
        return ReligionModel.builder()
                .idReligion(dto.getIdReligion())
                .religion(dto.getReligion())
                .build();
    }

    @Override
    public ReligionResponse toDto(ReligionModel entity) {
        return ReligionResponse.builder()
                .idReligion(entity.getIdReligion())
                .religion(entity.getReligion())
                .build();
    }

    @Override
    public List<ReligionResponse> toDtos(List<ReligionModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ReligionRequest request, ReligionModel entity) {
        entity.setReligion(request.getReligion());
    }
}