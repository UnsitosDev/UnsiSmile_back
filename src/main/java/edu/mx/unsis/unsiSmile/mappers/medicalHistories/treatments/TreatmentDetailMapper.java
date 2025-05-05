package edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class TreatmentDetailMapper implements BaseMapper<TreatmentDetailResponse, TreatmentDetailRequest, TreatmentDetailModel> {
    private final TreatmentMapper treatmentMapper;

    @Override
    public TreatmentDetailModel toEntity(TreatmentDetailRequest dto) {
        return TreatmentDetailModel.builder()
                .idTreatmentDetail(dto.getIdTreatmentDetail())
                .treatment(TreatmentModel.builder()
                        .idTreatment(dto.getTreatmentId())
                        .build())
                .startDate(dto.getStartDate() != null ?
                        dto.getStartDate() : null)
                .endDate(dto.getEndDate() != null ?
                        dto.getEndDate() : null)
                .status(ReviewStatus.IN_PROGRESS.toString())
                .build();
    }

    @Override
    public TreatmentDetailResponse toDto(TreatmentDetailModel entity) {
        return TreatmentDetailResponse.builder()
                .idTreatmentDetail(entity.getIdTreatmentDetail())
                .patientClinicalHistoryId(entity.getPatientClinicalHistory().getIdPatientClinicalHistory())
                .treatment(treatmentMapper.toDto(entity.getTreatment()))
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .studentGroupId(entity.getStudentGroup().getIdStudentGroups())
                .professorId(entity.getProfessor() != null ?
                        entity.getProfessor().getIdProfessor() : null)
                .status(entity.getStatus())
                .patientId(entity.getPatientClinicalHistory().getPatient().getIdPatient())
                .build();
    }

    @Override
    public List<TreatmentDetailResponse> toDtos(List<TreatmentDetailModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(TreatmentDetailRequest request, TreatmentDetailModel entity) {
        entity.setTreatment(TreatmentModel.builder()
                .idTreatment(request.getTreatmentId())
                .build());
        entity.setStartDate(request.getStartDate());
        entity.setEndDate(request.getEndDate());
    }
}