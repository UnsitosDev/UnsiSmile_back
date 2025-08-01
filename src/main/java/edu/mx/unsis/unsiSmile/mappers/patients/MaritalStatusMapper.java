package edu.mx.unsis.unsiSmile.mappers.patients;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.patients.demographics.MaritalStatusRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.demographics.MaritalStatusResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.patients.demographics.MaritalStatusModel;

@Component
public class MaritalStatusMapper implements BaseMapper<MaritalStatusResponse, MaritalStatusRequest, MaritalStatusModel> {

    @Override
    public MaritalStatusModel toEntity(MaritalStatusRequest dto) {
        return MaritalStatusModel.builder()
                .idMaritalStatus(dto.getIdMaritalStatus())
                .maritalStatus(dto.getMaritalStatus())
                .build();
    }

    @Override
    public MaritalStatusResponse toDto(MaritalStatusModel entity) {
        return MaritalStatusResponse.builder()
                .idMaritalStatus(entity.getIdMaritalStatus())
                .maritalStatus(entity.getMaritalStatus())
                .build();
    }

    @Override
    public List<MaritalStatusResponse> toDtos(List<MaritalStatusModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(MaritalStatusRequest request, MaritalStatusModel entity) {
        entity.setMaritalStatus(request.getMaritalStatus());
    }
}