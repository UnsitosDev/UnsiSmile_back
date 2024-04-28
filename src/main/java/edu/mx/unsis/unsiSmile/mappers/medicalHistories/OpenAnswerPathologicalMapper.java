package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OpenAnswerPathologicalRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OpenAnswerPathologicalResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.OpenAnswerPathologicalModel;

@Component
public class OpenAnswerPathologicalMapper implements BaseMapper<OpenAnswerPathologicalResponse, OpenAnswerPathologicalRequest, OpenAnswerPathologicalModel> {

    @Override
    public OpenAnswerPathologicalModel toEntity(OpenAnswerPathologicalRequest dto) {
        return OpenAnswerPathologicalModel.builder()
                .idOpenAnswerNonPathological(dto.getIdOpenAnswerNonPathological())
                .answer(dto.getAnswer())
                .build();
    }

    @Override
    public OpenAnswerPathologicalResponse toDto(OpenAnswerPathologicalModel entity) {
        return OpenAnswerPathologicalResponse.builder()
                .idOpenAnswerNonPathological(entity.getIdOpenAnswerNonPathological())
                .answer(entity.getAnswer())
                .build();
    }

    @Override
    public List<OpenAnswerPathologicalResponse> toDtos(List<OpenAnswerPathologicalModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(OpenAnswerPathologicalRequest request, OpenAnswerPathologicalModel entity) {
        // Implementa la lógica para actualizar la entidad según los datos de la solicitud
    }
}
