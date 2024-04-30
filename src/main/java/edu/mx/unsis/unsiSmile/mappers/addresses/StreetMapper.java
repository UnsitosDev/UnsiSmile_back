package edu.mx.unsis.unsiSmile.mappers.addresses;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.StreetRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.StreetResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.addresses.StreetModel;

@Component
public class StreetMapper implements BaseMapper<StreetResponse, StreetRequest, StreetModel> {

    @Autowired
    private NeighborhoodMapper neighborhoodMapper;

    @Override
    public StreetModel toEntity(StreetRequest dto) {
        return StreetModel.builder()
                .idStreet(dto.getIdStreet())
                .name(dto.getName())
                .neighborhood(neighborhoodMapper.toEntity(dto.getNeighborhood()))
                .build();
    }

    @Override
    public StreetResponse toDto(StreetModel entity) {
        return StreetResponse.builder()
                .idStreet(entity.getIdStreet())
                .name(entity.getName())
                .neighborhood(neighborhoodMapper.toDto(entity.getNeighborhood()))
                .build();
    }

    @Override
    public List<StreetResponse> toDtos(List<StreetModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(StreetRequest request, StreetModel entity) {
        // entity.setName(request.getName());
        // entity.setNeighborhood(NeighborhoodModel.builder().idNeighborhood(request.getNeighborhoodId()).build());
    }
}