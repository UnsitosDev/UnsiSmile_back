package edu.mx.unsis.unsiSmile.mappers.addresses;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.StateRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.StateResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.addresses.StateModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StateMapper implements BaseMapper<StateResponse, StateRequest, StateModel> {

    @Override
    public StateModel toEntity(StateRequest dto) {
        return StateModel.builder()
                .idState(dto.getIdState())
                .name(dto.getName())
                .build();
    }

    @Override
    public StateResponse toDto(StateModel entity) {
        return StateResponse.builder()
                .idState(entity.getIdState())
                .name(entity.getName())
                .build();
    }

    @Override
    public List<StateResponse> toDtos(List<StateModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(StateRequest request, StateModel entity) {
        entity.setName(request.getName());
    }
}