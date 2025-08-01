package edu.mx.unsis.unsiSmile.mappers.forms.sections;

import edu.mx.unsis.unsiSmile.dtos.request.forms.sections.FormSectionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.forms.sections.FormSectionResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.forms.sections.FormSectionModel;
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
                .requiresReview(dto.getRequiresReview())
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
                .requiresReview(entity.getRequiresReview())
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