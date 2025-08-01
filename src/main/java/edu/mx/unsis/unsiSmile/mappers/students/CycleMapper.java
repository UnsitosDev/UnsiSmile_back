package edu.mx.unsis.unsiSmile.mappers.students;

import edu.mx.unsis.unsiSmile.dtos.request.students.CycleRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.CycleResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.students.CycleModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CycleMapper implements BaseMapper<CycleResponse, CycleRequest, CycleModel>{

    @Override
    public CycleModel toEntity(CycleRequest dto) {
        return CycleModel.builder()
                .idCycle(dto.getIdCycle())
                .cycleName(dto.getCycleName())
                .build();
    }
    @Override
    public CycleResponse toDto(CycleModel entity) {
        return CycleResponse.builder()
                .idCycle(entity.getIdCycle())
                .cycleName(entity.getCycleName())
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
    }
}