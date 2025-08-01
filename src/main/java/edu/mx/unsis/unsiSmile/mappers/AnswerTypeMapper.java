package edu.mx.unsis.unsiSmile.mappers;


import edu.mx.unsis.unsiSmile.dtos.request.forms.answers.AnswerTypeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.forms.answers.AnswerTypeResponse;
import edu.mx.unsis.unsiSmile.model.forms.answers.AnswerTypeModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnswerTypeMapper implements BaseMapper<AnswerTypeResponse, AnswerTypeRequest, AnswerTypeModel> {
    @Override
    public AnswerTypeModel toEntity(AnswerTypeRequest dto) {
        return AnswerTypeModel.builder()
                .description(dto.getDescription())
                .build();
    }

    @Override
    public AnswerTypeResponse toDto(AnswerTypeModel entity) {
        return AnswerTypeResponse.builder()
                .idAnswerType(entity.getIdAnswerType())
                .description(entity.getDescription())
                .build();
    }

    @Override
    public List<AnswerTypeResponse> toDtos(List<AnswerTypeModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(AnswerTypeRequest request, AnswerTypeModel entity) {}
}
