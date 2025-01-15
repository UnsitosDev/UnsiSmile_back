package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.ClinicalHistoryCatalogModel;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IPatientClinicalHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientClinicalHistoryService {

    private final IPatientClinicalHistoryRepository patientClinicalHistoryRepository;

    @Transactional
    public PatientClinicalHistoryModel save(String idPatient, Long idClinicalHistory) {
        try {
            Optional<PatientClinicalHistoryModel> existingRecord =
                    patientClinicalHistoryRepository.findByPatient_IdPatientAndClinicalHistoryCatalog_IdClinicalHistoryCatalog(
                                    idPatient, idClinicalHistory);

            if (existingRecord.isPresent()) {
                throw new AppException("The clinical history already exists for this patient", HttpStatus.CONFLICT);
            }

            return patientClinicalHistoryRepository.save(toEntity(idPatient, idClinicalHistory));
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to save patient clinical history", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }


    @Transactional(readOnly = true)
    public PatientClinicalHistoryModel findById(Long id) {
        try {
            return patientClinicalHistoryRepository.findById(id)
                    .map(this::toDto)
                    .orElseThrow(() -> new AppException("Patient clinical history not found with ID: " + id, HttpStatus.NOT_FOUND));
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to find patient clinical history with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<PatientClinicalHistoryModel> findAll() {
        try {
            List<PatientClinicalHistoryModel> historyList = patientClinicalHistoryRepository.findAll();
            if (historyList.isEmpty()) {
                throw new AppException("No patient clinical histories found", HttpStatus.NOT_FOUND);
            }
            return historyList;
        } catch (Exception ex) {
            throw new AppException("Failed to fetch patient clinical histories", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<PatientClinicalHistoryModel> historyOptional = patientClinicalHistoryRepository.findById(id);
            historyOptional.ifPresentOrElse(
                    history -> {
                        history.setStatusKey(Constants.INACTIVE);
                        patientClinicalHistoryRepository.save(history);
                    },
                    () -> {
                        throw new AppException("Patient clinical history not found with ID: " + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete patient clinical history with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private PatientClinicalHistoryModel toEntity(String idPatient, Long idClinicalHistory) {
        return PatientClinicalHistoryModel.builder()
                .patient(PatientModel.builder()
                        .idPatient(idPatient)
                        .build())
                .clinicalHistoryCatalog(ClinicalHistoryCatalogModel.builder()
                        .idClinicalHistoryCatalog(idClinicalHistory)
                        .build())
                .date(LocalDateTime.now())
                .build();
    }

    @Transactional(readOnly = true)
    public List<PatientClinicalHistoryModel> findByPatient(String idPatient) {
        try {
            return patientClinicalHistoryRepository.findAllByPatientId(idPatient);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch patient clinical history", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public PatientClinicalHistoryModel findByPatientAndClinicalHistory(String idPatient, Long idClinicalHistory) {
        try {
            Optional<PatientClinicalHistoryModel> patientClinicalHistory = patientClinicalHistoryRepository
                    .findByPatient_IdPatientAndClinicalHistoryCatalog_IdClinicalHistoryCatalog(
                            idPatient, idClinicalHistory);
            if (patientClinicalHistory.isPresent()) {
                return this.toDto(patientClinicalHistory.get());
            } else {
                throw new AppException("Patient clinical history not found for idPatient: " + idPatient +
                        " and idClinicalHistory: " + idClinicalHistory, HttpStatus.NOT_FOUND);
            }
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to find patient clinical history with idPatient: " + idPatient + " and idClinicalHistory: " + idClinicalHistory, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private PatientClinicalHistoryModel toDto(PatientClinicalHistoryModel entity) {
        return PatientClinicalHistoryModel.builder()
                .idPatientClinicalHistory(entity.getIdPatientClinicalHistory())
                .patient(entity.getPatient())
                .clinicalHistoryCatalog(entity.getClinicalHistoryCatalog())
                .date(entity.getDate())
                .build();
    }
}