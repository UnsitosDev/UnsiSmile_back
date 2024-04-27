package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OpenQuestionPathologicalAntecedentsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OpenQuestionPathologicalAntecedentsResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.OpenQuestionsPathologicalAntecedentsModel;

@Component
public class OpenQuestionPathologicalAntecedentsMapper implements
        BaseMapper<OpenQuestionPathologicalAntecedentsResponse, OpenQuestionPathologicalAntecedentsRequest, OpenQuestionsPathologicalAntecedentsModel> {

    @Override
    public OpenQuestionsPathologicalAntecedentsModel toEntity(OpenQuestionPathologicalAntecedentsRequest request) {
        return OpenQuestionsPathologicalAntecedentsModel.builder()
                .idOpenQuestion(request.getIdOpenQuestion())
                .question(request.getQuestion())
                .build();
    }

    @Override
    public OpenQuestionPathologicalAntecedentsResponse toDto(OpenQuestionsPathologicalAntecedentsModel entity) {
        return OpenQuestionPathologicalAntecedentsResponse.builder()
                .idOpenQuestion(entity.getIdOpenQuestion())
                .question(entity.getQuestion())
                .build();
    }

    @Override
    public List<OpenQuestionPathologicalAntecedentsResponse> toDtos(
            List<OpenQuestionsPathologicalAntecedentsModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(OpenQuestionPathologicalAntecedentsRequest request,
            OpenQuestionsPathologicalAntecedentsModel entity) {
        entity.setQuestion(request.getQuestion());
    }
}
