package edu.mx.unsis.unsiSmile.mappers.addresses;

import java.util.List;
import java.util.stream.Collectors;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.HousingResponse;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.StreetResponse;
import edu.mx.unsis.unsiSmile.model.HousingModel;
import edu.mx.unsis.unsiSmile.model.addresses.StreetModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.AddressRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.AddressResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.addresses.AddressModel;

@Component
public class AddressMapper implements BaseMapper<AddressResponse, AddressRequest, AddressModel> {

    @Autowired
    private HousingMapper housingMapper;

    @Autowired
    private StreetMapper streetMapper;

    @Override
    public AddressModel toEntity(AddressRequest dto) {
        return AddressModel.builder()
                .idAddress(dto.getIdAddress())
                .streetNumber(dto.getStreetNumber())
                .interiorNumber(dto.getInteriorNumber())
                .housing(housingMapper.toEntity(dto.getHousing()))
                .street(streetMapper.toEntity(dto.getStreet()))
                .build();
    }

    @Override
    public AddressResponse toDto(AddressModel entity) {
        return AddressResponse.builder()
                .idAddress(entity.getIdAddress())
                .streetNumber(entity.getStreetNumber())
                .interiorNumber(entity.getInteriorNumber())
                .housing(housingMapper.toDto(entity.getHousing()))
                .street(streetMapper.toDto(entity.getStreet()))
                .build();
    }

    @Override
    public List<AddressResponse> toDtos(List<AddressModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(AddressRequest request, AddressModel entity) {
        // entity.setStreetNumber(request.getStreetNumber());
        // entity.setInteriorNumber(request.getInteriorNumber());
        // entity.setHousing(HousingModel.builder().idHousing(request.getHousingId()).build());
        // entity.setStreet(StreetModel.builder().idStreet(request.getStreetId()).build());
    }

    public AddressModel toModel(AddressRequest dto) {
        return AddressModel.builder()
                .idAddress(dto.getIdAddress())
                .streetNumber(dto.getStreetNumber())
                .interiorNumber(dto.getInteriorNumber())
                .housing(null)
                .street(null)
                .build();
    }
}