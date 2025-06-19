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
                .status(ReviewStatus.AWAITING_APPROVAL)
                .build();
    }

    @Override
    public TreatmentDetailResponse toDto(TreatmentDetailModel entity) {
        return TreatmentDetailResponse.builder()
                .idTreatmentDetail(entity.getIdTreatmentDetail())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .status(String.valueOf(entity.getStatus()))
                .treatment(treatmentMapper.toDto(entity.getTreatment()))
                .patient(TreatmentDetailResponse.PatientResponse.builder()
                        .id(entity.getPatientClinicalHistory().getPatient().getIdPatient())
                        .name(entity.getPatientClinicalHistory().getPatient().getPerson().getFullName())
                        .medicalRecordNumber(entity.getPatientClinicalHistory().getPatient().getMedicalRecordNumber())
                        .idPatientMedicalRecord(entity.getPatientClinicalHistory().getIdPatientClinicalHistory())
                        .build())
                .professor(entity.getProfessor() != null ?
                        TreatmentDetailResponse.ProfessorResponse.builder()
                                .id(entity.getProfessor().getIdProfessor())
                                .name(entity.getProfessor().getPerson().getFullName())
                                .build() :
                        null)
                .student(entity.getStudentGroup() != null ?
                        TreatmentDetailResponse.StudentResponse.builder()
                                .id(entity.getStudentGroup().getStudent().getEnrollment())
                                .name(entity.getStudentGroup().getStudent().getPerson().getFullName())
                                .group(entity.getStudentGroup().getGroup().getGroupName())
                                .idGroup(entity.getStudentGroup().getGroup().getIdGroup())
                                .build() :
                        null)
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
        entity.setEndDate(request.getEndDate());
        entity.setTreatment(TreatmentModel.builder()
                .idTreatment(request.getTreatmentId())
                .build());
    }
}