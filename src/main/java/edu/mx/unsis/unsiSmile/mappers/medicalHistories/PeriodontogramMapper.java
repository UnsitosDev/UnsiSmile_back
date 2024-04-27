package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.PeriodontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PeriodontogramResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.PeriodontogramModel;

@Component
public class PeriodontogramMapper implements BaseMapper<PeriodontogramResponse, PeriodontogramRequest, PeriodontogramModel> {

    @Override
    public PeriodontogramModel toEntity(PeriodontogramRequest dto) {
        return PeriodontogramModel.builder()
                .idPeriodontogram(dto.getIdPeriodontogram())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public PeriodontogramResponse toDto(PeriodontogramModel entity) {
        return PeriodontogramResponse.builder()
                .idPeriodontogram(entity.getIdPeriodontogram())
                .description(entity.getDescription())
                .date(entity.getDate().toString())
                .build();
    }

    @Override
    public List<PeriodontogramResponse> toDtos(List<PeriodontogramModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(PeriodontogramRequest request, PeriodontogramModel entity) {
        // Implementa la lógica para actualizar la entidad según los datos de la solicitud
    }
}
