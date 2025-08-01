package edu.mx.unsis.unsiSmile.mappers.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.components.FormComponentToothFaceConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.teeth.FormComponentToothFaceConditionResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.ToothFaceConditionMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.teeth.ToothFaceConditionModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.components.FormComponentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.components.FormComponentToothfaceConditionModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class FormComponentToothFaceConditionMapper implements BaseMapper<FormComponentToothFaceConditionResponse, FormComponentToothFaceConditionRequest, FormComponentToothfaceConditionModel> {
    private final FormComponentMapper formComponentMapper;
    private final ToothFaceConditionMapper toothFaceConditionMapper;

    @Override
    public FormComponentToothfaceConditionModel toEntity(FormComponentToothFaceConditionRequest dto) {
        return FormComponentToothfaceConditionModel.builder()
                .id(dto.getId())
                .formComponent(FormComponentModel.builder()
                        .idFormComponent(dto.getIdFormComponent())
                        .build())
                .toothfaceCondition(ToothFaceConditionModel.builder()
                        .idToothFaceCondition(dto.getIdToothFaceCondition())
                        .build())
                .build();
    }

    @Override
    public FormComponentToothFaceConditionResponse toDto(FormComponentToothfaceConditionModel entity) {
        return FormComponentToothFaceConditionResponse.builder()
                .id(entity.getId())
                .formComponent(formComponentMapper.toDto(entity.getFormComponent()))
                .toothFaceCondition(toothFaceConditionMapper.toDto(entity.getToothfaceCondition()))
                .build();
    }

    @Override
    public List<FormComponentToothFaceConditionResponse> toDtos(List<FormComponentToothfaceConditionModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(FormComponentToothFaceConditionRequest request, FormComponentToothfaceConditionModel entity) {
        entity.setFormComponent(FormComponentModel.builder().idFormComponent(request.getIdFormComponent()).build());
        entity.setToothfaceCondition(ToothFaceConditionModel.builder().idToothFaceCondition(request.getIdToothFaceCondition()).build());
    }
}
