package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ClosedQuestionPathologicalAntecedentsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ClosedQuestionPathologicalAntecedentsResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ClosedQuestionsPathologicalAntecedentsModel;

@Component
public class ClosedQuestionsPathologicalAntecedentsMapper implements BaseMapper<ClosedQuestionPathologicalAntecedentsResponse, ClosedQuestionPathologicalAntecedentsRequest, ClosedQuestionsPathologicalAntecedentsModel> {

    @Override
    public ClosedQuestionsPathologicalAntecedentsModel toEntity(ClosedQuestionPathologicalAntecedentsRequest request) {
        return ClosedQuestionsPathologicalAntecedentsModel.builder()
                .idClosedQuestion(request.getIdClosedQuestion())
                .question(request.getQuestion())
                .build();
    }

    @Override
    public ClosedQuestionPathologicalAntecedentsResponse toDto(ClosedQuestionsPathologicalAntecedentsModel entity) {
        return ClosedQuestionPathologicalAntecedentsResponse.builder()
                .idClosedQuestion(entity.getIdClosedQuestion())
                .question(entity.getQuestion())
                .build();
    }

    @Override
    public List<ClosedQuestionPathologicalAntecedentsResponse> toDtos(List<ClosedQuestionsPathologicalAntecedentsModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ClosedQuestionPathologicalAntecedentsRequest request, ClosedQuestionsPathologicalAntecedentsModel entity) {
        entity.setQuestion(request.getQuestion());
    }
}
