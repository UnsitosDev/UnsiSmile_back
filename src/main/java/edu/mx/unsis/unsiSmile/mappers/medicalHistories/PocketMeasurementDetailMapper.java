package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.PocketMeasurementDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PocketMeasurementDetailResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.PocketMeasurementDetailModel;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PocketMeasurementDetailMapper implements
        BaseMapper<PocketMeasurementDetailResponse, PocketMeasurementDetailRequest, PocketMeasurementDetailModel> {

    private ToothRegionPeriodontogramMapper toothRegionPeriodontogramMapper;
    private DentalCodeMapper dentalCodeMapper;
    private RegionMeasurementPocketsMapper regionMeasurementPocketsMapper;
    private PeriodontogramMapper periodontogramMapper;

    @Override
    public PocketMeasurementDetailModel toEntity(PocketMeasurementDetailRequest dto) {
        return PocketMeasurementDetailModel.builder()
                .idPocketMeasurementDetail(dto.getIdPocketMeasurementDetail())
                .toothRegionsPeriodontogram(
                        toothRegionPeriodontogramMapper.toEntity(dto.getToothRegionsPeriodontogram()))
                .dentalCode(dentalCodeMapper.toEntity(dto.getDentalCode()))
                .regionsMeasurementPockets(regionMeasurementPocketsMapper.toEntity(dto.getRegionsMeasurementPockets()))
                .periodontogram(periodontogramMapper.toEntity(dto.getPeriodontogram()))
                .measurement(dto.getMeasurement())
                .build();
    }

    @Override
    public PocketMeasurementDetailResponse toDto(PocketMeasurementDetailModel entity) {
        return PocketMeasurementDetailResponse.builder()
                .idPocketMeasurementDetail(entity.getIdPocketMeasurementDetail())
                .measurement(entity.getMeasurement())
                .dentalCode(dentalCodeMapper.toDto(entity.getDentalCode()))
                .toothRegionsPeriodontogram(
                        toothRegionPeriodontogramMapper.toDto(entity.getToothRegionsPeriodontogram()))
                .regionsMeasurementPockets(regionMeasurementPocketsMapper.toDto(entity.getRegionsMeasurementPockets()))
                .periodontogram(periodontogramMapper.toDto(entity.getPeriodontogram()))
                .build();
    }

    @Override
    public List<PocketMeasurementDetailResponse> toDtos(List<PocketMeasurementDetailModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(PocketMeasurementDetailRequest request, PocketMeasurementDetailModel entity) {
        // Implementa la lógica para actualizar la entidad según los datos de la
        // solicitud
    }
}
