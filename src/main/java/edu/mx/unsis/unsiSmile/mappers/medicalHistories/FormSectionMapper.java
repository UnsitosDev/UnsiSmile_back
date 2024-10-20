package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FormSectionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.FormSectionResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormSectionMapper implements BaseMapper<FormSectionResponse, FormSectionRequest, FormSectionModel> {

    @Override
    public FormSectionModel toEntity(FormSectionRequest dto) {
        return FormSectionModel.builder()
                .formName(dto.getFormName())
                .parentSection(FormSectionModel.builder()
                        .idFormSection(dto.getIdParentSection())
                        .build())
                .build();
    }

    @Override
    public FormSectionResponse toDto(FormSectionModel entity) {
        return FormSectionResponse.builder()
                .idFormSection(entity.getIdFormSection())
                .formName(entity.getFormName())
                .subSections(Collections.emptyList())
                .questions(Collections.emptyList())
                .build();
    }

    @Override
    public List<FormSectionResponse> toDtos(List<FormSectionModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(FormSectionRequest request, FormSectionModel entity) {}
}
