package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FacialFrontRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.FacialFrontResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.FacialFrontModel;

@Component
public class FacialFrontMapper implements BaseMapper<FacialFrontResponse, FacialFrontRequest, FacialFrontModel> {

    @Override
    public FacialFrontModel toEntity(FacialFrontRequest dto) {
        return FacialFrontModel.builder()
                .idFacialFront(dto.getIdFacialFront())
                .facialFront(dto.getFacialFront())
                .build();
    }

    @Override
    public FacialFrontResponse toDto(FacialFrontModel entity) {
        return FacialFrontResponse.builder()
                .idFacialFront(entity.getIdFacialFront())
                .facialFront(entity.getFacialFront())
                .build();
    }

    @Override
    public List<FacialFrontResponse> toDtos(List<FacialFrontModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(FacialFrontRequest request, FacialFrontModel entity) {
        entity.setFacialFront(request.getFacialFront());
    }
}
