package edu.mx.unsis.unsiSmile.mappers.medicalHistories;

import java.util.List;

import org.springframework.stereotype.Component;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.StatusClinicalHistoryRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.StatusClinicalHistoryResponse;
import edu.mx.unsis.unsiSmile.mappers.BaseMapper;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ClinicalHistoryStatus;
import edu.mx.unsis.unsiSmile.model.medicalHistories.StatusClinicalHistoryModel;

@Component
public class StatusClinicalHistoryMapper implements BaseMapper<StatusClinicalHistoryResponse, StatusClinicalHistoryRequest, StatusClinicalHistoryModel> {

    @Override
    public StatusClinicalHistoryModel toEntity(StatusClinicalHistoryRequest dto) {
        return StatusClinicalHistoryModel.builder()
                .status(ClinicalHistoryStatus.valueOf(dto.getStatus()))
                .message(dto.getMessage())
                .patientClinicalHistory(PatientClinicalHistoryModel.builder()
                        .idPatientClinicalHistory(dto.getIdPatientClinicalHistory())
                        .build())
                .build();
    }

    @Override
    public StatusClinicalHistoryResponse toDto(StatusClinicalHistoryModel entity) {
        return StatusClinicalHistoryResponse.builder()
                .idStatusClinicalHistory(entity.getIdStatusClinicalHistory())
                .status(entity.getStatus().toString())
                .message(entity.getMessage())
                .idPatientClinicalHistory(entity.getPatientClinicalHistory().getIdPatientClinicalHistory())
                .build();
    }

    @Override
    public List<StatusClinicalHistoryResponse> toDtos(List<StatusClinicalHistoryModel> entities) {
        throw new UnsupportedOperationException("Unimplemented method 'toDtos'");
    }

    @Override
    public void updateEntity(StatusClinicalHistoryRequest request, StatusClinicalHistoryModel entity) {
        throw new UnsupportedOperationException("Unimplemented method 'updateEntity'");
    }
}
