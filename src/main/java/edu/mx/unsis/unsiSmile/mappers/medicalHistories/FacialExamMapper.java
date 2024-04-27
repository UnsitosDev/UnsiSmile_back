package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FacialExamRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.FacialExamResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.FacialFrontResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.FacialProfileResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.FacialExamModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.FacialFrontModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.FacialProfileModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FacialExamMapper implements BaseMapper<FacialExamResponse, FacialExamRequest, FacialExamModel> {
    
    private FacialFrontMapper facialFrontMapper;
    private FacialProfileMapper facialProfileMapper;


    @Override
    public FacialExamModel toEntity(FacialExamRequest dto) {

        FacialFrontModel facialFront = facialFrontMapper.toEntity(dto.getFacialFront());

        FacialProfileModel FacialProfile = facialProfileMapper.toEntity(dto.getFacialProfile());
                                            
        return FacialExamModel.builder()
                .idFacialExam(dto.getIdFacialExam())
                .distinguishingFeatures(dto.getDistinguishingFeatures())
                .facialFront(facialFront)
                .facialProfile(FacialProfile)
                .build();
    }

    @Override
    public FacialExamResponse toDto(FacialExamModel entity) {

        FacialFrontResponse facialFrontResponse = facialFrontMapper.toDto(entity.getFacialFront());
        FacialProfileResponse facialProfileResponse = facialProfileMapper.toDto(entity.getFacialProfile());


        return FacialExamResponse.builder()
                .idFacialExam(entity.getIdFacialExam())
                .distinguishingFeatures(entity.getDistinguishingFeatures())
                .facialFront(facialFrontResponse)
                .facialProfile(facialProfileResponse)
                .build();
    }

    @Override
    public List<FacialExamResponse> toDtos(List<FacialExamModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(FacialExamRequest request, FacialExamModel entity) {
        entity.setDistinguishingFeatures(request.getDistinguishingFeatures());
    }
}
