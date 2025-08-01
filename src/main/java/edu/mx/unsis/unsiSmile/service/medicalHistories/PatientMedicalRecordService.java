package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.response.patients.PatientMedicalRecordRes;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.MedicalRecordCatalogMapper;
import edu.mx.unsis.unsiSmile.model.forms.catalogs.MedicalRecordCatalogModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientMedicalRecordModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IMedicalRecordCatalogRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IPatientMedicalRecordRepository;
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
public class PatientMedicalRecordService {

    private final IPatientMedicalRecordRepository patientMedicalRecordRepository;
    private final IPatientRepository patientRepository;
    private final MedicalRecordCatalogMapper medicalRecordCatalogMapper;
    private final IMedicalRecordCatalogRepository medicalRecordCatalogRepository;

    @Transactional
    public PatientMedicalRecordModel save(String idPatient, Long idMedicalRecordCatalog) {
        try {
            PatientModel patientModel = patientRepository.findByIdPatient(idPatient)
                    .orElseThrow(() -> new AppException(ResponseMessages.PATIENT_NOT_FOUND, HttpStatus.NOT_FOUND));
            MedicalRecordCatalogModel medicalRecordCatalogModel = medicalRecordCatalogRepository.findById(idMedicalRecordCatalog)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.CLINICAL_HISTORY_CATALOG_NOT_FOUND, idMedicalRecordCatalog),
                            HttpStatus.NOT_FOUND
                    ));
            return patientMedicalRecordRepository.save(toEntity(patientModel, medicalRecordCatalogModel));
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to save patient clinical history", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public PatientMedicalRecordRes createPatientMedicalRecord(String idPatient, Long idMedicalRecordCatalog) {
        try {
            PatientMedicalRecordModel model = this.save(idPatient, idMedicalRecordCatalog);
            return toResponse(model);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to save patient clinical history", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public PatientMedicalRecordModel findById(Long id) {
        try {
            return patientMedicalRecordRepository.findById(id)
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
    public List<PatientMedicalRecordModel> findAll() {
        try {
            List<PatientMedicalRecordModel> historyList = patientMedicalRecordRepository.findAll();
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
            Optional<PatientMedicalRecordModel> historyOptional = patientMedicalRecordRepository.findById(id);
            historyOptional.ifPresentOrElse(
                    history -> {
                        history.setStatusKey(Constants.INACTIVE);
                        patientMedicalRecordRepository.save(history);
                    },
                    () -> {
                        throw new AppException("Patient clinical history not found with ID: " + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete patient clinical history with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private PatientMedicalRecordModel toEntity(PatientModel patient, MedicalRecordCatalogModel medicalRecordCatalogModel) {
        return PatientMedicalRecordModel.builder()
                .patient(patient)
                .medicalRecordCatalog(medicalRecordCatalogModel)
                .appointmentDate(LocalDateTime.now())
                .build();
    }

    @Transactional
    public PatientMedicalRecordModel findGeneralMedicalRecordByPatientId(String idPatient) {
        try {
            PatientMedicalRecordModel existingRecord = findExistingGeneralMedicalRecord(idPatient);
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

    private PatientMedicalRecordModel findExistingGeneralMedicalRecord(String idPatient) {
        PatientMedicalRecordModel existing = patientMedicalRecordRepository
                .findFirstByPatient_IdPatientAndMedicalRecordCatalog_MedicalRecordNameOrderByCreatedAtDesc(idPatient, Constants.GENERAL_MEDICAL_RECORD);

        return existing != null ? this.toDto(existing) : null;
    }

    @Transactional
    public PatientMedicalRecordModel createNewGeneralMedicalRecord(String idPatient) {
        try {
            PatientMedicalRecordModel existingRecord = findExistingGeneralMedicalRecord(idPatient);
            if (existingRecord != null) {
                throw new AppException(ResponseMessages.DUPLICATED_GENERAL_MEDICAL_RECORD,
                        HttpStatus.CONFLICT);
            }
            MedicalRecordCatalogModel catalog = medicalRecordCatalogRepository
                    .findByMedicalRecordName(Constants.GENERAL_MEDICAL_RECORD)
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.GENERAL_MEDICAL_RECORD_NOT_FOUND,
                            HttpStatus.NOT_FOUND));

            PatientMedicalRecordModel modelSaved = save(idPatient, catalog.getIdMedicalRecordCatalog());
            return this.toDto(modelSaved);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_GENERAL_MEDICAL_RECORD, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public PatientMedicalRecordModel findByPatientAndMedicalRecord(String idPatient, Long idPatientMedicalRecord) {
        try {
            Optional<PatientMedicalRecordModel> patientMedicalRecord = patientMedicalRecordRepository
                    .findByPatient_IdPatientAndIdPatientMedicalRecord(
                            idPatient, idPatientMedicalRecord);
            if (patientMedicalRecord.isPresent()) {
                return this.toDto(patientMedicalRecord.get());
            } else {
                throw new AppException("Patient clinical history not found for idPatient: " + idPatient +
                        " and idPatientMedicalRecord: " + idPatientMedicalRecord, HttpStatus.NOT_FOUND);
            }
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to find patient clinical history with idPatient: " + idPatient + " and idPatientMedicalRecord: " + idPatientMedicalRecord, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private PatientMedicalRecordModel toDto(PatientMedicalRecordModel entity) {
        return PatientMedicalRecordModel.builder()
                .idPatientMedicalRecord(entity.getIdPatientMedicalRecord())
                .patient(entity.getPatient())
                .medicalRecordCatalog(entity.getMedicalRecordCatalog())
                .appointmentDate(entity.getAppointmentDate())
                .build();
    }

    private PatientMedicalRecordRes toResponse(PatientMedicalRecordModel entity) {
        return PatientMedicalRecordRes.builder()
                .idPatientMedicalRecord(entity.getIdPatientMedicalRecord())
                .medicalRecordCatalog(medicalRecordCatalogMapper.toDto(entity.getMedicalRecordCatalog()))
                .appointmentDate(entity.getAppointmentDate().toLocalDate())
                .build();
    }

    @Transactional
    public PatientMedicalRecordModel updatePatientMedicalRecord(Long id, Long idMedicalRecordCatalog) {
        try {
            PatientMedicalRecordModel patientMedicalRecordModel = patientMedicalRecordRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.PATIENT_CLINICAL_HISTORY_NOT_FOUND, id)
                            , HttpStatus.NOT_FOUND));
            MedicalRecordCatalogModel medicalRecordCatalogModel = medicalRecordCatalogRepository.findById(idMedicalRecordCatalog)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.CLINICAL_HISTORY_CATALOG_NOT_FOUND, idMedicalRecordCatalog),
                            HttpStatus.NOT_FOUND
                    ));
            patientMedicalRecordModel.setMedicalRecordCatalog(medicalRecordCatalogModel);
            return patientMedicalRecordRepository.save(patientMedicalRecordModel);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to save patient clinical history", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}