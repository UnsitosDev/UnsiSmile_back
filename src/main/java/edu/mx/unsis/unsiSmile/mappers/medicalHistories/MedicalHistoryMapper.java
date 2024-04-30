package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.MedicalHistoryRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.MedicalHistoryResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.MedicalHistoryModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class MedicalHistoryMapper
        implements BaseMapper<MedicalHistoryResponse, MedicalHistoryRequest, MedicalHistoryModel> {

    private FacialExamMapper facialExamMapper;
    private NonPathologicalPersonalAntecedentsMapper nonPathologicalPersonalAntecedentsMapper;
    private OdontogramMapper odontogramMapper;

    @Override
    public MedicalHistoryModel toEntity(MedicalHistoryRequest dto) {
        return MedicalHistoryModel.builder()
                .idMedicalHistory(dto.getIdMedicalHistory())
                .facialExam(dto.getFacialExam() != null ? facialExamMapper.toEntity(dto.getFacialExam()) : null)
                .nonPathologicalPersonalAntecedents(dto.getNonPathologicalPersonalAntecedents() != null
                        ? nonPathologicalPersonalAntecedentsMapper.toEntity(dto.getNonPathologicalPersonalAntecedents())
                        : null)
                .initialOdontogram(
                        dto.getInitialOdontogram() != null ? odontogramMapper.toEntity(dto.getInitialOdontogram())
                                : null)
                .finalOdontogram(
                        dto.getFinalOdontogram() != null ? odontogramMapper.toEntity(dto.getFinalOdontogram()) : null)
                .build();
    }

    @Override
    public MedicalHistoryResponse toDto(MedicalHistoryModel entity) {
        return MedicalHistoryResponse.builder()
                .idMedicalHistory(entity.getIdMedicalHistory())
                .facialExamId(
                        entity.getFacialExam() != null ? entity.getFacialExam().getIdFacialExam()
                                : null)
                .nonPathologicalPersonalAntecedentsId(
                        entity.getNonPathologicalPersonalAntecedents() != null
                                ? entity.getNonPathologicalPersonalAntecedents()
                                        .getIdNonPathologicalPersonalAntecedents()
                                : null)
                .initialOdontogramId(entity.getInitialOdontogram() != null
                        ? entity.getInitialOdontogram().getIdOdontogram()
                        : null)
                .finalOdontogramId(entity.getFinalOdontogram() != null
                        ? entity.getFinalOdontogram().getIdOdontogram()
                        : null)
                .build();
    }

    @Override
    public List<MedicalHistoryResponse> toDtos(List<MedicalHistoryModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(MedicalHistoryRequest request, MedicalHistoryModel entity) {
        // Implementa la lógica para actualizar la entidad según los datos de la
        // solicitud
    }
}
