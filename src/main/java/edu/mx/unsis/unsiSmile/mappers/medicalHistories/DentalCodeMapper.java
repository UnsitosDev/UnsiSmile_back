package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.DentalCodeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.DentalCodeResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.DentalCodeModel;

@Component
public class DentalCodeMapper implements BaseMapper<DentalCodeResponse, DentalCodeRequest, DentalCodeModel> {

    @Override
    public DentalCodeModel toEntity(DentalCodeRequest dto) {
        return DentalCodeModel.builder()
                .idDentalCode(dto.getIdDentalCode())
                .code(dto.getCode())
                .adult(dto.getAdult())
                .build();
    }

    @Override
    public DentalCodeResponse toDto(DentalCodeModel entity) {
        return DentalCodeResponse.builder()
                .idDentalCode(entity.getIdDentalCode())
                .code(entity.getCode())
                .adult(entity.getAdult())
                .build();
    }

    @Override
    public List<DentalCodeResponse> toDtos(List<DentalCodeModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(DentalCodeRequest request, DentalCodeModel entity) {
        entity.setCode(request.getCode());
        entity.setAdult(request.getAdult());
    }
}
