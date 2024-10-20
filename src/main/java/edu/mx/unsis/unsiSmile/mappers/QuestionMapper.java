package edu.mx.unsis.unsiSmile.mappers;

import edu.mx.unsis.unsiSmile.dtos.request.QuestionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.QuestionResponse;
import edu.mx.unsis.unsiSmile.model.AnswerTypeModel;
import edu.mx.unsis.unsiSmile.model.CatalogModel;
import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.QuestionModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class QuestionMapper implements BaseMapper<QuestionResponse, QuestionRequest, QuestionModel> {

    @Override
    public QuestionModel toEntity(QuestionRequest dto) {
        return QuestionModel.builder()
                .questionText(dto.getQuestionText())
                .placeholder(dto.getPlaceholder())
                .required(dto.getIsRequired())
                .order(dto.getOrder())
                .formSectionModel(FormSectionModel.builder()
                        .idFormSection(dto.getIdFormSection())
                        .build())
                .answerTypeModel(AnswerTypeModel.builder()
                        .idAnswerType(dto.getIdAnswerType())
                        .build())
                .catalogModel(dto.getIdCatalog() != 0 ?
                        CatalogModel.builder()
                                .idCatalog(dto.getIdCatalog()).build() : null)
                .build();
    }

    @Override
    public QuestionResponse toDto(QuestionModel entity) {
        return QuestionResponse.builder()
                .idQuestion(entity.getIdQuestion())
                .questionText(entity.getQuestionText())
                .placeholder(entity.getPlaceholder())
                .required(entity.getRequired())
                .order(entity.getOrder())
                .build();
    }

    @Override
    public List<QuestionResponse> toDtos(List<QuestionModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(QuestionRequest request, QuestionModel entity) {
    }
}
