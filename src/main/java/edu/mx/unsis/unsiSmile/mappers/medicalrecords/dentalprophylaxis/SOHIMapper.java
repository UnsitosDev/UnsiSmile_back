package edu.mx.unsis.unsiSmile.mappers.medicalrecords.dentalprophylaxis;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.dentalprophylaxis.ToothCodeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.dentalprophylaxis.ToothCodeResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.dentalprophylaxis.SOHIModel;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentDetailModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SOHIMapper implements BaseMapper<ToothCodeResponse, ToothCodeRequest, SOHIModel> {

    @Override
    public SOHIModel toEntity(ToothCodeRequest dto) {
        return SOHIModel.builder()
                .treatmentDetail(TreatmentDetailModel.builder()
                        .idTreatmentDetail(dto.getIdTreatment())
                        .build())
                .build();
    }

    @Override
    public ToothCodeResponse toDto(SOHIModel entity) {
        return ToothCodeResponse.builder()
                .id(entity.getIdSohi())
                .idTreatment(entity.getTreatmentDetail().getIdTreatmentDetail())
                .teeth(Collections.emptyList())
                .build();
    }

    @Override
    public List<ToothCodeResponse> toDtos(List<SOHIModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ToothCodeRequest request, SOHIModel entity) {
    }   
}
