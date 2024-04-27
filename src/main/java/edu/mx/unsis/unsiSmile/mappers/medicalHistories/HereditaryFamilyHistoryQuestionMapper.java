package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.HereditaryFamilyHistoryQuestionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.HereditaryFamilyHistoryQuestionResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.HereditaryFamilyHistoryQuestionModel;

@Component
public class HereditaryFamilyHistoryQuestionMapper implements BaseMapper<HereditaryFamilyHistoryQuestionResponse, HereditaryFamilyHistoryQuestionRequest, HereditaryFamilyHistoryQuestionModel> {

    @Override
    public HereditaryFamilyHistoryQuestionModel toEntity(HereditaryFamilyHistoryQuestionRequest dto) {
        return HereditaryFamilyHistoryQuestionModel.builder()
                .idQuestion(dto.getIdQuestion())
                .question(dto.getQuestion())
                .build();
    }

    @Override
    public HereditaryFamilyHistoryQuestionResponse toDto(HereditaryFamilyHistoryQuestionModel entity) {
        return HereditaryFamilyHistoryQuestionResponse.builder()
                .idQuestion(entity.getIdQuestion())
                .question(entity.getQuestion())
                .build();
    }

    @Override
    public List<HereditaryFamilyHistoryQuestionResponse> toDtos(List<HereditaryFamilyHistoryQuestionModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(HereditaryFamilyHistoryQuestionRequest request, HereditaryFamilyHistoryQuestionModel entity) {
        entity.setQuestion(request.getQuestion());
    }
}
