package edu.mx.unsis.unsiSmile.mappers.professors;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.professors.ClinicalAreaRequest;
import edu.mx.unsis.unsiSmile.dtos.response.professors.ClinicalAreaResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.professors.ClinicalAreaModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ClinicalAreaMapper implements BaseMapper<ClinicalAreaResponse, ClinicalAreaRequest, ClinicalAreaModel>{
    
    @Override
    public ClinicalAreaModel toEntity(ClinicalAreaRequest dto) {
        if (dto == null) {
            return null;
        }
        return ClinicalAreaModel.builder()
                .idClinicalArea(dto.getIdClinicalArea())
                .clinicalArea(dto.getClinicalArea())
                .build();
    }

    @Override
    public ClinicalAreaResponse toDto(ClinicalAreaModel entity) {
        if (entity == null) {
            return null;
        }
        return ClinicalAreaResponse.builder()
                .idClinicalArea(entity.getIdClinicalArea())
                .clinicalArea(entity.getClinicalArea())
                .build();
    }

    @Override
    public List<ClinicalAreaResponse> toDtos(List<ClinicalAreaModel> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public void updateEntity(ClinicalAreaRequest request, ClinicalAreaModel entity) {
        if (request == null || entity == null) {
            return;
        }
        entity.setIdClinicalArea(request.getIdClinicalArea());
        entity.setClinicalArea(request.getClinicalArea());
    }
}
