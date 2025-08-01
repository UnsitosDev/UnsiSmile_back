package edu.mx.unsis.unsiSmile.mappers.forms.questions;

import edu.mx.unsis.unsiSmile.dtos.request.forms.questions.ValidationRequest;
import edu.mx.unsis.unsiSmile.dtos.response.forms.questions.ValidationResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.forms.questions.ValidationModel;
import edu.mx.unsis.unsiSmile.model.forms.questions.ValidationTypeModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidationMapper implements BaseMapper<ValidationResponse, ValidationRequest, ValidationModel> {
    @Override
    public ValidationModel toEntity(ValidationRequest dto) {
        return ValidationModel.builder()
                .validationValue(dto.getValidationValue())
                .validationMessage(dto.getValidationMessage())
                .validationTypeModel(ValidationTypeModel.builder()
                        .idValidationType(dto.getIdValidationType())
                        .build())
                .build();
    }

    @Override
    public ValidationResponse toDto(ValidationModel entity) {
        return ValidationResponse.builder()
                .idValidation(entity.getIdValidation())
                .validationValue(entity.getValidationValue())
                .validationMessage(entity.getValidationMessage())
                .build();
    }

    @Override
    public List<ValidationResponse> toDtos(List<ValidationModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ValidationRequest request, ValidationModel entity) {}
}
