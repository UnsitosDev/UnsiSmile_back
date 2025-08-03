package edu.mx.unsis.unsiSmile.mappers.medicalrecords.fluorosis;

import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.fluorosis.DeanIndexToothCodeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.fluorosis.DeanIndexToothCodeResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.fluorosis.DeanIndexModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientMedicalRecordModel;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DeanIndexMapper implements BaseMapper<DeanIndexToothCodeResponse, DeanIndexToothCodeRequest, DeanIndexModel> {

    @Override
    public DeanIndexModel toEntity(DeanIndexToothCodeRequest dto) {
        return DeanIndexModel.builder()
                .patientMedicalRecord(PatientMedicalRecordModel.builder()
                        .idPatientMedicalRecord(dto.getIdPatientMedicalRecord())
                        .build())
                .build();
    }

    @Override
    public DeanIndexToothCodeResponse toDto(DeanIndexModel entity) {
        return DeanIndexToothCodeResponse.builder()
                .id(entity.getIdDeanIndex())
                .idPatientMedicalRecord(entity.getPatientMedicalRecord().getIdPatientMedicalRecord())
                .teeth(Collections.emptyList())
                .build();
    }

    @Override
    public List<DeanIndexToothCodeResponse> toDtos(List<DeanIndexModel> entities) {
        return entities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateEntity(DeanIndexToothCodeRequest request, DeanIndexModel entity) {
    }
}