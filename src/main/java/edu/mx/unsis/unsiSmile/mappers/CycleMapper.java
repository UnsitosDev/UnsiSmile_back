package edu.mx.unsis.unsiSmile.mappers;

import edu.mx.unsis.unsiSmile.dtos.request.CycleRequest;
import edu.mx.unsis.unsiSmile.dtos.response.CycleResponse;
import edu.mx.unsis.unsiSmile.model.CycleModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CycleMapper implements BaseMapper<CycleResponse, CycleRequest, CycleModel>{
    @Override
    public CycleModel toEntity(CycleRequest dto) {
        return CycleModel.builder()
                .cycleName(dto.getCycleName())
                .status(dto.getStatus())
                .build();
    }
    @Override
    public CycleResponse toDto(CycleModel entity) {
        return CycleResponse.builder()
                .idCycle(entity.getIdCycle())
                .cycleName(entity.getCycleName())
                .status(entity.getStatus())
                .build();
        }

    @Override
    public List<CycleResponse> toDtos(List<CycleModel>  entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(CycleRequest request, CycleModel entity) {
        entity.setCycleName(request.getCycleName());
        entity.setStatus(request.getStatus());
    }
}
