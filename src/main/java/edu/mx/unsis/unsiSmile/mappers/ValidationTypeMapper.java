package edu.mx.unsis.unsiSmile.mappers;

import edu.mx.unsis.unsiSmile.dtos.request.forms.questions.ValidationTypeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.forms.questions.ValidationTypeResponse;
import edu.mx.unsis.unsiSmile.model.forms.questions.ValidationTypeModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ValidationTypeMapper implements BaseMapper<ValidationTypeResponse, ValidationTypeRequest, ValidationTypeModel> {
    @Override
    public ValidationTypeModel toEntity(ValidationTypeRequest dto) {
        return ValidationTypeModel.builder()
                .validationCode(dto.getValidationCode())
                .build();
    }

    @Override
    public ValidationTypeResponse toDto(ValidationTypeModel entity) {
        return ValidationTypeResponse.builder()
                .idValidationType(entity.getIdValidationType())
                .validationCode(entity.getValidationCode())
                .build();
    }

    @Override
    public List<ValidationTypeResponse> toDtos(List<ValidationTypeModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ValidationTypeRequest request, ValidationTypeModel entity) {}
}
