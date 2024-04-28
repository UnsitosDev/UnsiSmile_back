package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.MedicalHistoryRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.MedicalHistoryResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.MedicalHistoryModel;

@Component
public class MedicalHistoryMapper implements BaseMapper<MedicalHistoryResponse, MedicalHistoryRequest, MedicalHistoryModel> {

    @Override
    public MedicalHistoryModel toEntity(MedicalHistoryRequest dto) {
        return MedicalHistoryModel.builder()
                .idMedicalHistory(dto.getIdMedicalHistory())
                .build();
    }

    @Override
    public MedicalHistoryResponse toDto(MedicalHistoryModel entity) {
        return MedicalHistoryResponse.builder()
                .idMedicalHistory(entity.getIdMedicalHistory())
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
        // Implementa la lógica para actualizar la entidad según los datos de la solicitud
    }
}
