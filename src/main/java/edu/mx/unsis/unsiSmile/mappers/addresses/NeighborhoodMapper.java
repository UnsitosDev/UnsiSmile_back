package edu.mx.unsis.unsiSmile.mappers.addresses;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.NeighborhoodRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.NeighborhoodResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.addresses.NeighborhoodModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class NeighborhoodMapper implements BaseMapper<NeighborhoodResponse, NeighborhoodRequest, NeighborhoodModel> {

    @Autowired
    private LocalityMapper localityMapper;

    @Override
    public NeighborhoodModel toEntity(NeighborhoodRequest dto) {
        return NeighborhoodModel.builder()
                .idNeighborhood(dto.getIdNeighborhood())
                .name(dto.getName())
                .locality(localityMapper.toEntity(dto.getLocality()))
                .build();
    }

    @Override
    public NeighborhoodResponse toDto(NeighborhoodModel entity) {
        return NeighborhoodResponse.builder()
                .idNeighborhood(entity.getIdNeighborhood())
                .name(entity.getName())
                .locality(entity.getLocality() != null ? localityMapper.toDto(entity.getLocality()) : null)
                .build();
    }

    @Override
    public List<NeighborhoodResponse> toDtos(List<NeighborhoodModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(NeighborhoodRequest request, NeighborhoodModel entity) {
        // entity.setName(request.getName());
        // entity.setLocality(LocalityModel.builder().idLocality(request.getLocalityId()).build());
    }

    public NeighborhoodModel toModel(NeighborhoodRequest dto) {
        return NeighborhoodModel.builder()
                .idNeighborhood(dto.getIdNeighborhood())
                .name(dto.getName())
                .locality(null)
                .build();
    }
}