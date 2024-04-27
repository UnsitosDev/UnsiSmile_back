package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothDetailResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ToothDetailModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ToothDetailMapper implements BaseMapper<ToothDetailResponse, ToothDetailRequest, ToothDetailModel> {

    private DentalCodeMapper dentalCodeMapper;
    private ToothConditionMapper toothConditionMapper;
    private ToothRegionMapper toothRegionMapper;
    private OdontogramMapper odontogramMapper;

    @Override
    public ToothDetailModel toEntity(ToothDetailRequest dto) {
        return ToothDetailModel.builder()
                .idToothDetail(dto.getIdToothDetail())
                .dentalCode( dentalCodeMapper.toEntity(dto.getDentalCode()))
                .toothCondition(toothConditionMapper.toEntity(dto.getToothConditionRequest()))
                .toothRegion(toothRegionMapper.toEntity(dto.getToothRegionRequest()))
                .odontogram(odontogramMapper.toEntity(dto.getOdontogramRequest()))
                .build();
    }

    @Override
    public ToothDetailResponse toDto(ToothDetailModel entity) {
        return ToothDetailResponse.builder()
                .idToothDetail(entity.getIdToothDetail())
                .dentalCode(dentalCodeMapper.toDto(entity.getDentalCode()))
                .toothCondition(toothConditionMapper.toDto(entity.getToothCondition()))
                .toothRegion(toothRegionMapper.toDto(entity.getToothRegion()))
                .odontogram(odontogramMapper.toDto(entity.getOdontogram()))
                .build();
    }

    @Override
    public List<ToothDetailResponse> toDtos(List<ToothDetailModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(ToothDetailRequest request, ToothDetailModel entity) {
        // Implementa la lógica para actualizar la entidad según los datos de la solicitud
    }
}
