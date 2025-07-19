package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PatientMedicalRecordRes;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.MedicalRecordCatalogMapper;
import edu.mx.unsis.unsiSmile.model.MedicalRecordCatalogModel;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IMedicalRecordCatalogRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IPatientClinicalHistoryRepository;
import edu.mx.unsis.unsiSmile.repository.patients.IPatientRepository;
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
    private final IPatientRepository patientRepository;
    private final MedicalRecordCatalogMapper medicalRecordCatalogMapper;
    private final IMedicalRecordCatalogRepository medicalRecordCatalogRepository;

    @Transactional
    public PatientClinicalHistoryModel save(String idPatient, Long idMedicalRecordCatalog) {
        try {
            PatientModel patientModel = patientRepository.findByIdPatient(idPatient)
                    .orElseThrow(() -> new AppException(ResponseMessages.PATIENT_NOT_FOUND, HttpStatus.NOT_FOUND));
            MedicalRecordCatalogModel medicalRecordCatalogModel = medicalRecordCatalogRepository.findById(idMedicalRecordCatalog)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.CLINICAL_HISTORY_CATALOG_NOT_FOUND, idMedicalRecordCatalog),
                            HttpStatus.NOT_FOUND
                    ));
            return patientClinicalHistoryRepository.save(toEntity(patientModel, medicalRecordCatalogModel));
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to save patient clinical history", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public PatientMedicalRecordRes createPatientMedicalRecord(String idPatient, Long idMedicalRecordCatalog) {
        try {
            PatientClinicalHistoryModel model = this.save(idPatient, idMedicalRecordCatalog);
            return toResponse(model);
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
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.PATIENT_CLINICAL_HISTORY_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));
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

    private PatientClinicalHistoryModel toEntity(PatientModel patient, MedicalRecordCatalogModel medicalRecordCatalogModel) {
        return PatientClinicalHistoryModel.builder()
                .patient(patient)
                .medicalRecordCatalog(medicalRecordCatalogModel)
                .appointmentDate(LocalDateTime.now())
                .build();
    }

    @Transactional
    public PatientClinicalHistoryModel findGeneralMedicalRecordByPatientId(String idPatient) {
        try {
            PatientClinicalHistoryModel existingRecord = findExistingGeneralMedicalRecord(idPatient);
            if (existingRecord == null) {
                throw new AppException(ResponseMessages.GENERAL_MEDICAL_RECORD_NOT_FOUND_FOR_PATIENT,
                        HttpStatus.NOT_FOUND);
            }
            return existingRecord;
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_GENERAL_MEDICAL_RECORD,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private PatientClinicalHistoryModel findExistingGeneralMedicalRecord(String idPatient) {
        PatientClinicalHistoryModel existing = patientClinicalHistoryRepository
                .findFirstByPatient_IdPatientAndMedicalRecordCatalog_MedicalRecordNameOrderByCreatedAtDesc(idPatient, Constants.GENERAL_MEDICAL_RECORD);

        return existing != null ? this.toDto(existing) : null;
    }

    @Transactional
    public PatientClinicalHistoryModel createNewGeneralMedicalRecord(String idPatient) {
        try {
            PatientClinicalHistoryModel existingRecord = findExistingGeneralMedicalRecord(idPatient);
            if (existingRecord != null) {
                throw new AppException(ResponseMessages.DUPLICATED_GENERAL_MEDICAL_RECORD,
                        HttpStatus.CONFLICT);
            }
            MedicalRecordCatalogModel catalog = medicalRecordCatalogRepository
                    .findByMedicalRecordName(Constants.GENERAL_MEDICAL_RECORD)
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.GENERAL_MEDICAL_RECORD_NOT_FOUND,
                            HttpStatus.NOT_FOUND));

            PatientClinicalHistoryModel modelSaved = save(idPatient, catalog.getIdMedicalRecordCatalog());
            return this.toDto(modelSaved);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_GENERAL_MEDICAL_RECORD, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public PatientClinicalHistoryModel findByPatientAndClinicalHistory(String idPatient, Long idPatientClinicalHistory) {
        try {
            Optional<PatientClinicalHistoryModel> patientClinicalHistory = patientClinicalHistoryRepository
                    .findByPatient_IdPatientAndIdPatientClinicalHistory(
                            idPatient, idPatientClinicalHistory);
            if (patientClinicalHistory.isPresent()) {
                return this.toDto(patientClinicalHistory.get());
            } else {
                throw new AppException("Patient clinical history not found for idPatient: " + idPatient +
                        " and idPatientClinicalHistory: " + idPatientClinicalHistory, HttpStatus.NOT_FOUND);
            }
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to find patient clinical history with idPatient: " + idPatient + " and idClinicalHistory: " + idPatientClinicalHistory, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private PatientClinicalHistoryModel toDto(PatientClinicalHistoryModel entity) {
        return PatientClinicalHistoryModel.builder()
                .idPatientClinicalHistory(entity.getIdPatientClinicalHistory())
                .patient(entity.getPatient())
                .medicalRecordCatalog(entity.getMedicalRecordCatalog())
                .appointmentDate(entity.getAppointmentDate())
                .build();
    }

    private PatientMedicalRecordRes toResponse(PatientClinicalHistoryModel entity) {
        return PatientMedicalRecordRes.builder()
                .idPatientClinicalHistory(entity.getIdPatientClinicalHistory())
                .medicalRecordCatalog(medicalRecordCatalogMapper.toDto(entity.getMedicalRecordCatalog()))
                .appointmentDate(entity.getAppointmentDate().toLocalDate())
                .build();
    }

    @Transactional
    public PatientClinicalHistoryModel updatePatientMedicalRecord(Long id, Long idClinicalHistory) {
        try {
            PatientClinicalHistoryModel patientClinicalHistoryModel = patientClinicalHistoryRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.PATIENT_CLINICAL_HISTORY_NOT_FOUND, id)
                            , HttpStatus.NOT_FOUND));
            MedicalRecordCatalogModel medicalRecordCatalogModel = medicalRecordCatalogRepository.findById(idClinicalHistory)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.CLINICAL_HISTORY_CATALOG_NOT_FOUND, idClinicalHistory),
                            HttpStatus.NOT_FOUND
                    ));
            patientClinicalHistoryModel.setMedicalRecordCatalog(medicalRecordCatalogModel);
            return patientClinicalHistoryRepository.save(patientClinicalHistoryModel);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to save patient clinical history", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}