package edu.mx.unsis.unsiSmile.mappers.addresses;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.MunicipalityRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.MunicipalityResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.addresses.MunicipalityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MunicipalityMapper implements BaseMapper<MunicipalityResponse, MunicipalityRequest, MunicipalityModel> {

    @Autowired
    private StateMapper stateMapper;

    @Override
    public MunicipalityModel toEntity(MunicipalityRequest dto) {
        return MunicipalityModel.builder()
                .idMunicipality(dto.getIdMunicipality())
                .name(dto.getName())
                .state(stateMapper.toEntity(dto.getState()))
                .build();
    }

    @Override
    public MunicipalityResponse toDto(MunicipalityModel entity) {
        return MunicipalityResponse.builder()
                .idMunicipality(entity.getIdMunicipality())
                .name(entity.getName())
                .state(stateMapper.toDto(entity.getState()))
                .build();
    }

    @Override
    public List<MunicipalityResponse> toDtos(List<MunicipalityModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(MunicipalityRequest request, MunicipalityModel entity) {
        // entity.setName(request.getName());
        // entity.setState(StateModel.builder().idState(request.getStateId()).build());
    }

    public MunicipalityModel toModel(MunicipalityRequest dto) {
        return MunicipalityModel.builder()
                .idMunicipality(dto.getIdMunicipality())
                .name(dto.getName())
                .state(null)
                .build();
    }
}