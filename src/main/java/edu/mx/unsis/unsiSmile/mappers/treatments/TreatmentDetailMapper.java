package edu.mx.unsis.unsiSmile.mappers.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.response.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.enums.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.treatments.AuthorizedTreatmentModel;
import edu.mx.unsis.unsiSmile.model.treatments.ExecutionReviewModel;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentModel;
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
                .startDate(entity.getStartDate().toLocalDate())
                .endDate(entity.getEndDate().toLocalDate())
                .status(String.valueOf(entity.getStatus()))
                .treatment(treatmentMapper.toDto(entity.getTreatment()))
                .patient(TreatmentDetailResponse.PatientResponse.builder()
                        .id(entity.getPatientMedicalRecord().getPatient().getIdPatient())
                        .name(entity.getPatientMedicalRecord().getPatient().getPerson().getFullName())
                        .medicalRecordNumber(entity.getPatientMedicalRecord().getPatient().getMedicalRecordNumber())
                        .idPatientMedicalRecord(entity.getPatientMedicalRecord().getIdPatientMedicalRecord())
                        .build())
                .student(entity.getStudentGroup() != null ?
                        TreatmentDetailResponse.StudentResponse.builder()
                                .id(entity.getStudentGroup().getStudent().getEnrollment())
                                .name(entity.getStudentGroup().getStudent().getPerson().getFullName())
                                .group(entity.getStudentGroup().getGroup().getFullGroupName())
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
    }

    public TreatmentDetailResponse toDtoWithAuthorizingProfessor(AuthorizedTreatmentModel model) {
        TreatmentDetailResponse response = toDto(model.getTreatmentDetail());

        response.setApprovalProfessor(TreatmentDetailResponse.ProfessorResponse.builder()
                .idProfessorClinicalArea(model.getProfessorClinicalArea().getIdProfessorClinicalArea())
                .professorName(model.getProfessorClinicalArea().getProfessor().getPerson().getFullName())
                .comments(model.getComment())
                .build());

        return response;
    }

    public TreatmentDetailResponse toDtoWithReviewerProfessor(ExecutionReviewModel model) {
        TreatmentDetailResponse response = toDto(model.getTreatmentDetail());

        response.setReviewProfessor(TreatmentDetailResponse.ProfessorResponse.builder()
                .idProfessorClinicalArea(model.getProfessorClinicalArea().getIdProfessorClinicalArea())
                .professorName(model.getProfessorClinicalArea().getProfessor().getPerson().getFullName())
                .comments(model.getComment())
                .build());

        return response;
    }

    public void setAuthorizingProfessor(TreatmentDetailResponse response, AuthorizedTreatmentModel model) {
        response.setApprovalProfessor(TreatmentDetailResponse.ProfessorResponse.builder()
                .idProfessorClinicalArea(model.getProfessorClinicalArea().getIdProfessorClinicalArea())
                .professorName(model.getProfessorClinicalArea().getProfessor().getPerson().getFullName())
                .comments(model.getComment())
                .build());
    }

    public void setReviewerProfessor(TreatmentDetailResponse response, ExecutionReviewModel model) {
        response.setReviewProfessor(TreatmentDetailResponse.ProfessorResponse.builder()
                .idProfessorClinicalArea(model.getProfessorClinicalArea().getIdProfessorClinicalArea())
                .professorName(model.getProfessorClinicalArea().getProfessor().getPerson().getFullName())
                .comments(model.getComment())
                .build());
    }
}