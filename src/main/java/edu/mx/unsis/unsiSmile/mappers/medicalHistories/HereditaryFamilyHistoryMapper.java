package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.HereditaryFamilyHistoryRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.HereditaryFamilyHistoryResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.HereditaryFamilyHistoryModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class HereditaryFamilyHistoryMapper implements BaseMapper<HereditaryFamilyHistoryResponse, HereditaryFamilyHistoryRequest, HereditaryFamilyHistoryModel> {

    private final HereditaryFamilyHistoryQuestionMapper questionMapper;

    @Override
    public HereditaryFamilyHistoryModel toEntity(HereditaryFamilyHistoryRequest dto) {

        return HereditaryFamilyHistoryModel.builder()
                .idFamilyHistory(dto.getIdFamilyHistory())
                .question(questionMapper.toEntity(dto.getFamilyHistoryQuestion()))
                .mainResponse(HereditaryFamilyHistoryModel.Response.valueOf(dto.getMainResponse()))
                .responseDetail(dto.getResponseDetail())
                .build();
    }

    @Override
    public HereditaryFamilyHistoryResponse toDto(HereditaryFamilyHistoryModel entity) {

        return HereditaryFamilyHistoryResponse.builder()
                .idFamilyHistory(entity.getIdFamilyHistory())
                .question(questionMapper.toDto(entity.getQuestion()))
                .mainResponse(entity.getMainResponse().name())
                .responseDetail(entity.getResponseDetail())
                .build();
    }

    @Override
    public List<HereditaryFamilyHistoryResponse> toDtos(List<HereditaryFamilyHistoryModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(HereditaryFamilyHistoryRequest request, HereditaryFamilyHistoryModel entity) {
        //entity.setQuestion(questionMapper.toEntity(request.get));
        entity.setMainResponse(HereditaryFamilyHistoryModel.Response.valueOf(request.getMainResponse()));
        entity.setResponseDetail(request.getResponseDetail());
    }
}
