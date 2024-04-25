package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FacialProfileRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.FacialProfileResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.FacialProfileModel;

@Component
public class FacialProfileMapper implements BaseMapper<FacialProfileResponse, FacialProfileRequest, FacialProfileModel> {

    @Override
    public FacialProfileModel toEntity(FacialProfileRequest dto) {
        return FacialProfileModel.builder()
                .facialProfile(dto.getFacialProfile())
                .build();
    }

    @Override
    public FacialProfileResponse toDto(FacialProfileModel entity) {
        return FacialProfileResponse.builder()
                .idFacialProfile(entity.getIdFacialProfile())
                .facialProfile(entity.getFacialProfile())
                .build();
    }

    @Override
    public List<FacialProfileResponse> toDtos(List<FacialProfileModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(FacialProfileRequest request, FacialProfileModel entity) {
        entity.setFacialProfile(request.getFacialProfile());
    }
}
