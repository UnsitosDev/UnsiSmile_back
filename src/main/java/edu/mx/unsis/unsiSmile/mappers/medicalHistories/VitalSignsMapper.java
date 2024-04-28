package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.VitalSignsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.VitalSignsResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.VitalSignsModel;

@Component
public class VitalSignsMapper implements BaseMapper<VitalSignsResponse, VitalSignsRequest, VitalSignsModel> {

    @Override
    public VitalSignsModel toEntity(VitalSignsRequest dto) {
        return VitalSignsModel.builder()
                .idVitalSigns(dto.getIdVitalSigns())
                .weight(dto.getWeight())
                .height(dto.getHeight())
                .temperature(dto.getTemperature())
                .heartRate(dto.getHeartRate())
                .respiratoryRate(dto.getRespiratoryRate())
                .bloodPressure(dto.getBloodPressure())
                .oxygenSaturation(dto.getOxygenSaturation())
                .glucose(dto.getGlucose())
                .pulse(dto.getPulse())
                .build();
    }

    @Override
    public VitalSignsResponse toDto(VitalSignsModel entity) {
        return VitalSignsResponse.builder()
                .idVitalSigns(entity.getIdVitalSigns())
                .weight(entity.getWeight())
                .height(entity.getHeight())
                .temperature(entity.getTemperature())
                .heartRate(entity.getHeartRate())
                .respiratoryRate(entity.getRespiratoryRate())
                .bloodPressure(entity.getBloodPressure())
                .oxygenSaturation(entity.getOxygenSaturation())
                .glucose(entity.getGlucose())
                .pulse(entity.getPulse())
                .build();
    }

    @Override
    public List<VitalSignsResponse> toDtos(List<VitalSignsModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(VitalSignsRequest request, VitalSignsModel entity) {
        entity.setWeight(request.getWeight());
        entity.setHeight(request.getHeight());
        entity.setTemperature(request.getTemperature());
        entity.setHeartRate(request.getHeartRate());
        entity.setRespiratoryRate(request.getRespiratoryRate());
        entity.setBloodPressure(request.getBloodPressure());
        entity.setOxygenSaturation(request.getOxygenSaturation());
        entity.setGlucose(request.getGlucose());
        entity.setPulse(request.getPulse());
    }
}