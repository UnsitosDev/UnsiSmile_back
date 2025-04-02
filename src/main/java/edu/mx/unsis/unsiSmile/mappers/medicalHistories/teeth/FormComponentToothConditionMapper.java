package edu.mx.unsis.unsiSmile.mappers.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.teeth.FormComponentToothConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.teeth.FormComponentToothConditionResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.ToothConditionMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.FormComponentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.FormComponentToothConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothConditionModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FormComponentToothConditionMapper implements BaseMapper<FormComponentToothConditionResponse, FormComponentToothConditionRequest, FormComponentToothConditionModel> {
    private final FormComponentMapper formComponentMapper;
    private final ToothConditionMapper toothConditionMapper;

    @Override
    public FormComponentToothConditionModel toEntity(FormComponentToothConditionRequest dto) {
        return FormComponentToothConditionModel.builder()
                .id(dto.getId())
                .formComponent(FormComponentModel.builder()
                        .idFormComponent(dto.getIdFormComponent())
                        .build())
                .toothCondition(ToothConditionModel.builder()
                        .idToothCondition(dto.getIdToothCondition())
                        .build())
                .build();
    }

    @Override
    public FormComponentToothConditionResponse toDto(FormComponentToothConditionModel entity) {
        return FormComponentToothConditionResponse.builder()
                .id(entity.getId())
                .formComponent(formComponentMapper.toDto(entity.getFormComponent()))
                .toothCondition(toothConditionMapper.toDto(entity.getToothCondition()))
                .build();
    }

    @Override
    public List<FormComponentToothConditionResponse> toDtos(List<FormComponentToothConditionModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(FormComponentToothConditionRequest request, FormComponentToothConditionModel entity) {
        entity.setFormComponent(FormComponentModel.builder().idFormComponent(request.getIdFormComponent()).build());
        entity.setToothCondition(ToothConditionModel.builder().idToothCondition(request.getIdToothCondition()).build());
    }
}
