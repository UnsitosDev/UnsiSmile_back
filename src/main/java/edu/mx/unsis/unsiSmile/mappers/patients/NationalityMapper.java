package edu.mx.unsis.unsiSmile.mappers.patients;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.patients.NationalityRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.NationalityResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.NationalityModel;

@Component
public class NationalityMapper implements BaseMapper<NationalityResponse, NationalityRequest, NationalityModel> {

    @Override
    public NationalityModel toEntity(NationalityRequest dto) {
        return NationalityModel.builder()
                .idNationality(dto.getIdNationality())
                .nationality(dto.getNationality())
                .build();
    }

    @Override
    public NationalityResponse toDto(NationalityModel entity) {
        return NationalityResponse.builder()
                .idNationality(entity.getIdNationality())
                .nationality(entity.getNationality())
                .build();
    }

    @Override
    public List<NationalityResponse> toDtos(List<NationalityModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(NationalityRequest request, NationalityModel entity) {
        entity.setNationality(request.getNationality());
    }
}