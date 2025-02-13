package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.StatusClinicalHistoryRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.StatusClinicalHistoryResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.StatusClinicalHistoryMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.StatusClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IStatusClinicalHistoryRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StatusClinicalHistoryService {

    private final IStatusClinicalHistoryRepository statusClinicalHistoryRepository;
    private final StatusClinicalHistoryMapper statusClinicalHistoryMapper;

    @Transactional
    public void createOrUpdateStatusClinicalHistory(StatusClinicalHistoryRequest request) {
        try {
            Optional<StatusClinicalHistoryModel> existingStatus = 
                statusClinicalHistoryRepository.findByPatientClinicalHistory_IdPatientClinicalHistory(request.getIdPatientClinicalHistory());

            StatusClinicalHistoryModel statusModel;

            if (existingStatus.isPresent()) {
                statusModel = existingStatus.get();
                statusModel.setStatus(request.getStatus());
                statusModel.setMessage(request.getMessage());
            } else {
                statusModel = statusClinicalHistoryMapper.toEntity(request);
            }

            statusClinicalHistoryRepository.save(statusModel);
        } catch (Exception e) {
            throw new AppException("Error while processing the status of the clinical history", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public StatusClinicalHistoryResponse getStatusByPatientClinicalHistoryId(Long idPatientClinicalHistory) {
        try {
            StatusClinicalHistoryModel statusModel = 
                statusClinicalHistoryRepository.findByPatientClinicalHistory_IdPatientClinicalHistory(idPatientClinicalHistory)
                    .orElseThrow(() -> new AppException("Status not found for the given patient clinical history", HttpStatus.NOT_FOUND));

            return statusClinicalHistoryMapper.toDto(statusModel);

        } catch (AppException ex) {
            throw ex;
        } catch (Exception e) {
            throw new AppException("Error while fetching the status of the clinical history", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public void deleteStatusByPatientClinicalHistoryId(Long idPatientClinicalHistory) {
        try {
            StatusClinicalHistoryModel statusModel = 
                statusClinicalHistoryRepository.findByPatientClinicalHistory_IdPatientClinicalHistory(idPatientClinicalHistory)
                    .orElseThrow(() -> new AppException("Status not found for the given patient clinical history", HttpStatus.NOT_FOUND));
            
            statusModel.setStatusKey(Constants.INACTIVE);
            
            statusClinicalHistoryRepository.save(statusModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception e) {
            throw new AppException("Error while deleting the status of the clinical history", HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }
}
