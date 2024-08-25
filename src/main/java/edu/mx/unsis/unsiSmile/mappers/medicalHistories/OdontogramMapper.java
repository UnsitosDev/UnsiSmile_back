package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OdontogramResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.OdontogramModel;

@Component
public class OdontogramMapper implements BaseMapper<OdontogramResponse, OdontogramRequest, OdontogramModel> {

    @Override
    public OdontogramModel toEntity(OdontogramRequest dto) {
        return OdontogramModel.builder()
                .idOdontogram(dto.getIdOdontogram())
                .build();
    }

    @Override
    public OdontogramResponse toDto(OdontogramModel entity) {
        return OdontogramResponse.builder()
                .idOdontogram(entity.getIdOdontogram())
                .date(entity.getCreationDate().toString())
                .build();
    }

    @Override
    public List<OdontogramResponse> toDtos(List<OdontogramModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(OdontogramRequest request, OdontogramModel entity) {

    }
}
