package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ClosedAnswerPathologicalRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ClosedAnswerPathologicalResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ClosedAnswerPathologicalModel;

@Component
public class ClosedAnswerPathologicalMapper implements BaseMapper<ClosedAnswerPathologicalResponse, ClosedAnswerPathologicalRequest, ClosedAnswerPathologicalModel> {

    @Override
    public ClosedAnswerPathologicalModel toEntity(ClosedAnswerPathologicalRequest dto) {
        return ClosedAnswerPathologicalModel.builder()
                .idClosedAnswerNonPathological(dto.getIdClosedAnswerNonPathological())
                .answer(dto.getAnswer())
                .build();
    }

    @Override
    public ClosedAnswerPathologicalResponse toDto(ClosedAnswerPathologicalModel entity) {
        return ClosedAnswerPathologicalResponse.builder()
                .idClosedAnswerNonPathological(entity.getIdClosedAnswerNonPathological())
                .answer(entity.getAnswer())
                .build();
    }

    @Override
    public List<ClosedAnswerPathologicalResponse> toDtos(List<ClosedAnswerPathologicalModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ClosedAnswerPathologicalRequest request, ClosedAnswerPathologicalModel entity) {
        // Implementa la lógica para actualizar la entidad según los datos de la solicitud
    }
}
