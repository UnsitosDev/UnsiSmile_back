package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.StatusClinicalHistoryRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.StatusClinicalHistoryResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.StatusClinicalHistoryMapper;
import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ClinicalHistoryStatus;
import edu.mx.unsis.unsiSmile.model.medicalHistories.StatusClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IStatusClinicalHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusClinicalHistoryService {

    private final IStatusClinicalHistoryRepository statusClinicalHistoryRepository;
    private final StatusClinicalHistoryMapper statusClinicalHistoryMapper;

    @Transactional
    public void createOrUpdateStatusClinicalHistory(StatusClinicalHistoryRequest request) {
        try {
            Optional<StatusClinicalHistoryModel> existingStatus = 
                statusClinicalHistoryRepository.findByPatientClinicalHistory_IdPatientClinicalHistoryAndFormSection_IdFormSection(
                        request.getIdPatientClinicalHistory(), request.getIdSection());

            StatusClinicalHistoryModel statusModel;

            if (existingStatus.isPresent()) {
                statusModel = existingStatus.get();
                statusModel.setStatus(ClinicalHistoryStatus.valueOf(request.getStatus()));
                statusModel.setMessage(request.getMessage());
            } else {
                statusModel = statusClinicalHistoryMapper.toEntity(request);
            }

            statusClinicalHistoryRepository.save(statusModel);
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_PROCESSING_STATUS, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public StatusClinicalHistoryResponse getStatusByPatientClinicalHistoryId(Long idPatientClinicalHistory, Long idSection) {
        try {
            StatusClinicalHistoryModel statusModel = 
                statusClinicalHistoryRepository.findByPatientClinicalHistory_IdPatientClinicalHistoryAndFormSection_IdFormSection(idPatientClinicalHistory, idSection)
                        .orElseThrow(() -> new AppException(ResponseMessages.STATUS_NOT_FOUND, HttpStatus.NOT_FOUND));

            return statusClinicalHistoryMapper.toDto(statusModel);

        } catch (AppException ex) {
            throw ex;
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_FETCHING_STATUS, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public void sendToReview(Long idPatientClinicalHistory,  Long idSection) {
        try {
            StatusClinicalHistoryModel statusModel = 
                statusClinicalHistoryRepository.findByPatientClinicalHistory_IdPatientClinicalHistoryAndFormSection_IdFormSection(idPatientClinicalHistory, idSection)
                    .orElseGet(() -> StatusClinicalHistoryModel.builder()
                        .patientClinicalHistory(PatientClinicalHistoryModel.builder()
                                .idPatientClinicalHistory(idPatientClinicalHistory)
                                .build())
                        .formSection(FormSectionModel.builder()
                                .idFormSection(idSection)
                                .build())
                        .status(ClinicalHistoryStatus.IN_REVIEW)
                        .build());

            statusModel.setStatus(ClinicalHistoryStatus.IN_REVIEW);
            statusModel.setMessage(ResponseMessages.CLINICAL_HISTORY_SENT_TO_REVIEW);

            statusClinicalHistoryRepository.save(statusModel);

        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_SENDING_TO_REVIEW, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public Page<StatusClinicalHistoryResponse> getStatusClinicalHistoriesByStatus(String status, Pageable pageable) {
        try {
            ClinicalHistoryStatus clinicalHistoryStatus = ClinicalHistoryStatus.valueOf(status.toUpperCase());

            Page<StatusClinicalHistoryModel> statusModels = statusClinicalHistoryRepository.findByStatus(clinicalHistoryStatus, pageable);

            return statusModels.map(statusClinicalHistoryMapper::toDto);

        } catch (IllegalArgumentException e) {
            throw new AppException("Estatus no válido: " + status, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new AppException(ResponseMessages.ERROR_FETCHING_STATUS_LIST, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public StatusClinicalHistoryModel getStatusByPatientClinicalHistoryIdAndSection(Long idPatientClinicalHistory, Long idSection) {
        return statusClinicalHistoryRepository.findByPatientClinicalHistory_IdPatientClinicalHistoryAndFormSection_IdFormSection(idPatientClinicalHistory, idSection)
                            .orElse(null);
    }
}
