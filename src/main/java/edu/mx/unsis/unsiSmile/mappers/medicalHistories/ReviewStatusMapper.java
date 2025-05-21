package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ReviewStatusRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ReviewSectionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ReviewStatusResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatusModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ReviewStatusMapper implements BaseMapper<ReviewStatusResponse, ReviewStatusRequest, ReviewStatusModel> {

    @Override
    public ReviewStatusModel toEntity(ReviewStatusRequest dto) {
        return ReviewStatusModel.builder()
                .status(dto.getStatus())
                .message(dto.getMessage())
                .build();
    }

    @Override
    public ReviewStatusResponse toDto(ReviewStatusModel entity) {
        return ReviewStatusResponse.builder()
                .idReviewStatus(entity.getIdReviewStatus())
                .status(entity.getStatus().toString())
                .message(entity.getMessage())
                .idPatientMedicalRecord(entity.getPatientClinicalHistory().getIdPatientClinicalHistory())
                .idProfessorClinicalArea(entity.getProfessorClinicalArea().getIdProfessorClinicalArea())
                .idSection(entity.getFormSection().getIdFormSection())
                .patient(ReviewStatusResponse.PatientResp.builder()
                        .name(entity.getPatientClinicalHistory().getPatient().getPerson().getFullName())
                        .curp(entity.getPatientClinicalHistory().getPatient().getPerson().getCurp())
                        .medicalRecordNumber(entity.getPatientClinicalHistory().getPatient().getMedicalRecordNumber())
                        .build())
                .idReviewStatus(entity.getIdReviewStatus())
                .status(entity.getStatus().toString())
                .message(entity.getMessage())
                .build();
    }

    @Override
    public List<ReviewStatusResponse> toDtos(List<ReviewStatusModel> entities) {
        throw new UnsupportedOperationException("Unimplemented method 'toDtos'");
    }

    @Override
    public void updateEntity(ReviewStatusRequest request, ReviewStatusModel entity) {
        entity.setStatus(request.getStatus());
        entity.setMessage(request.getMessage());
    }

    public ReviewStatusModel toEntity(Long idPatientClinicalHistory, Long idSection, Long idProfessorClinicalArea) {
        return ReviewStatusModel.builder()
                .status(ReviewStatus.IN_REVIEW)
                .message(null)
                .patientClinicalHistory(PatientClinicalHistoryModel.builder()
                        .idPatientClinicalHistory(idPatientClinicalHistory)
                        .build())
                .formSection(FormSectionModel.builder()
                        .idFormSection(idSection)
                        .build())
                .professorClinicalArea(ProfessorClinicalAreaModel.builder()
                        .idProfessorClinicalArea(idProfessorClinicalArea)
                        .build())
                .build();
    }

    public ReviewSectionResponse toReviewSectionResponse(ReviewStatusModel entity) {
        return ReviewSectionResponse.builder()
                .idReviewStatus(entity.getIdReviewStatus())
                .status(entity.getStatus().toString())
                .message(entity.getMessage())
                .build();
    }
}