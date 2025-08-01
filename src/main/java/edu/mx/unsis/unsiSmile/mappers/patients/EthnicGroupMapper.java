package edu.mx.unsis.unsiSmile.mappers.patients;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.patients.demographics.EthnicGroupRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.EthnicGroupResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.patients.demographics.EthnicGroupModel;

@Component
public class EthnicGroupMapper implements BaseMapper<EthnicGroupResponse, EthnicGroupRequest, EthnicGroupModel> {

    @Override
    public EthnicGroupModel toEntity(EthnicGroupRequest dto) {
        return EthnicGroupModel.builder()
                .idEthnicGroup(dto.getIdEthnicGroup())
                .ethnicGroup(dto.getEthnicGroup())
                .build();
    }

    @Override
    public EthnicGroupResponse toDto(EthnicGroupModel entity) {
        return EthnicGroupResponse.builder()
                .idEthnicGroup(entity.getIdEthnicGroup())
                .ethnicGroup(entity.getEthnicGroup())
                .build();
    }

    @Override
    public List<EthnicGroupResponse> toDtos(List<EthnicGroupModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(EthnicGroupRequest request, EthnicGroupModel entity) {
        entity.setEthnicGroup(request.getEthnicGroup());
    }
}