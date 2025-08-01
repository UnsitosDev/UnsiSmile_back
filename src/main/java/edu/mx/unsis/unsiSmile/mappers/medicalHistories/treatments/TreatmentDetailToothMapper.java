package edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentDetailToothRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailToothResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentDetailToothModel;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TreatmentDetailToothMapper implements BaseMapper<TreatmentDetailToothResponse, TreatmentDetailToothRequest, TreatmentDetailToothModel> {

    @Override
    public TreatmentDetailToothModel toEntity(TreatmentDetailToothRequest dto) {
        return TreatmentDetailToothModel.builder()
                .treatmentDetail(TreatmentDetailModel.builder()
                        .idTreatmentDetail(dto.getIdTreatmentDetail())
                        .build())
                .startDate(LocalDateTime.now())
                .build();
    }

    @Override
    public TreatmentDetailToothResponse toDto(TreatmentDetailToothModel entity) {
        return TreatmentDetailToothResponse.builder()
                .idDetailTooth(entity.getIdDetailTooth())
                .idTooth(entity.getTooth().getIdTooth())
                .endDate(entity.getEndDate() != null ? entity.getEndDate().toLocalDate() : null)
                .status(entity.getStatus() != null ? entity.getStatus().getStatus().toString() : null)
                .build();
    }

    @Override
    public List<TreatmentDetailToothResponse> toDtos(List<TreatmentDetailToothModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(TreatmentDetailToothRequest request, TreatmentDetailToothModel entity) {
        entity.setTreatmentDetail(TreatmentDetailModel.builder()
                .idTreatmentDetail(request.getIdTreatmentDetail())
                .build());
    }
}