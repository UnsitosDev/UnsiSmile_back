package edu.mx.unsis.unsiSmile.mappers.addresses;
import java.util.List;
import java.util.stream.Collectors;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.MunicipalityResponse;
import edu.mx.unsis.unsiSmile.model.addresses.MunicipalityModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.LocalityRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.LocalityResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.addresses.LocalityModel;

@Component
public class LocalityMapper implements BaseMapper<LocalityResponse, LocalityRequest, LocalityModel> {

    @Autowired
    private MunicipalityMapper municipalityMapper;

    @Override
    public LocalityModel toEntity(LocalityRequest dto) {
        return LocalityModel.builder()
                .idLocality(dto.getIdLocality())
                .name(dto.getName())
                .postalCode(dto.getPostalCode())
                .municipality(municipalityMapper.toEntity(dto.getMunicipality()))
                .build();
    }

    @Override
    public LocalityResponse toDto(LocalityModel entity) {
        return LocalityResponse.builder()
                .idLocality(entity.getIdLocality())
                .name(entity.getName())
                .postalCode(entity.getPostalCode())
                .municipality(municipalityMapper.toDto(entity.getMunicipality()))
                .build();
    }

    @Override
    public List<LocalityResponse> toDtos(List<LocalityModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(LocalityRequest request, LocalityModel entity) {
        // entity.setName(request.getName());
        // entity.setPostalCode(request.getPostalCode());
        // entity.setMunicipality(MunicipalityModel.builder().idMunicipality(request.getMunicipalityId()).build());
    }

    public LocalityModel toModel(LocalityRequest dto) {
        return LocalityModel.builder()
                .idLocality(dto.getIdLocality())
                .name(dto.getName())
                .postalCode(dto.getPostalCode())
                .municipality(null)
                .build();
    }
}