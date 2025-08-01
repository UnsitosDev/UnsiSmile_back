package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.DeanIndexToothCodeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.DeanIndexToothCodeResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.DeanIndexModel;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentDetailModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeanIndexMapper implements BaseMapper<DeanIndexToothCodeResponse, DeanIndexToothCodeRequest, DeanIndexModel> {
    @Override
    public DeanIndexModel toEntity(DeanIndexToothCodeRequest dto) {
        return DeanIndexModel.builder()
                .treatmentDetail(TreatmentDetailModel.builder()
                        .idTreatmentDetail(dto.getIdTreatment())
                        .build())
                .build();
    }

    @Override
    public DeanIndexToothCodeResponse toDto(DeanIndexModel entity) {
        return DeanIndexToothCodeResponse.builder()
                .id(entity.getIdDeanIndex())
                .idTreatment(entity.getTreatmentDetail().getIdTreatmentDetail())
                .teeth(Collections.emptyList())
                .build();
    }

    @Override
    public List<DeanIndexToothCodeResponse> toDtos(List<DeanIndexModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(DeanIndexToothCodeRequest request, DeanIndexModel entity) {
    }
}