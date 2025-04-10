package edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ClinicalHistoryStatus;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentScopeModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class TreatmentDetailMapper implements BaseMapper<TreatmentDetailResponse, TreatmentDetailRequest, TreatmentDetailModel> {
    private final TreatmentMapper treatmentMapper;
    private final TreatmentScopeMapper treatmentScopeMapper;

    @Override
    public TreatmentDetailModel toEntity(TreatmentDetailRequest dto) {
        return TreatmentDetailModel.builder()
                .idTreatmentDetail(dto.getIdTreatmentDetail())
                .treatment(TreatmentModel.builder()
                        .idTreatment(dto.getTreatmentId())
                        .build())
                .treatmentScope(TreatmentScopeModel.builder()
                                .idScope(dto.getTreatmentScopeId())
                                .build())
                .treatmentDate(dto.getTreatmentDate() != null ?
                        dto.getTreatmentDate() : null)
                .professor(dto.getProfessorId() != null ? ProfessorModel.builder()
                        .idProfessor(dto.getProfessorId())
                        .build() : null)
                .status(ClinicalHistoryStatus.IN_PROGRESS.toString())
                .build();
    }

    @Override
    public TreatmentDetailResponse toDto(TreatmentDetailModel entity) {
        return TreatmentDetailResponse.builder()
                .idTreatmentDetail(entity.getIdTreatmentDetail())
                .patientClinicalHistoryId(entity.getPatientClinicalHistory().getIdPatientClinicalHistory())
                .treatment(treatmentMapper.toDto(entity.getTreatment()))
                .treatmentScope(treatmentScopeMapper.toDto(entity.getTreatmentScope()))
                .treatmentDate(entity.getTreatmentDate())
                .studentGroupId(entity.getStudentGroup().getIdStudentGroups())
                .professorId(entity.getProfessor() != null ?
                        entity.getProfessor().getIdProfessor() : null)
                .status(entity.getStatus())
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
        entity.setTreatmentScope(request.getTreatmentScopeId() != null ?
                TreatmentScopeModel.builder()
                        .idScope(request.getTreatmentScopeId())
                        .build() : null);
        entity.setTreatmentDate(request.getTreatmentDate());
        entity.setProfessor(ProfessorModel.builder()
                .idProfessor(request.getProfessorId())
                .build());
        entity.setStatus(request.getStatus());
    }
}