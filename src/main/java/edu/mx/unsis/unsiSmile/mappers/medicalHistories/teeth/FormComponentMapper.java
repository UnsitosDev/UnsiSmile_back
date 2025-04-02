package edu.mx.unsis.unsiSmile.mappers.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.teeth.FormComponentRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.teeth.FormComponentResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.FormComponentModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormComponentMapper implements BaseMapper<FormComponentResponse, FormComponentRequest, FormComponentModel> {

    @Override
    public FormComponentModel toEntity(FormComponentRequest dto) {
        return FormComponentModel.builder()
                .idFormComponent(dto.getIdFormComponent())
                .description(dto.getDescription())
                .build();
    }

    @Override
    public FormComponentResponse toDto(FormComponentModel entity) {
        return FormComponentResponse.builder()
                .idFormComponent(entity.getIdFormComponent())
                .description(entity.getDescription())
                .build();
    }

    @Override
    public List<FormComponentResponse> toDtos(List<FormComponentModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(FormComponentRequest request, FormComponentModel entity) {
        entity.setDescription(request.getDescription());
    }
}
