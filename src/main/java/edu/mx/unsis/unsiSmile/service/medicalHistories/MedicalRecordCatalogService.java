package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.MedicalRecordCatalogRequest;
import edu.mx.unsis.unsiSmile.dtos.response.FormSectionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.MedicalRecordCatalogResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PatientMedicalRecordResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.MedicalRecordCatalogMapper;
import edu.mx.unsis.unsiSmile.model.MedicalRecordCatalogModel;
import edu.mx.unsis.unsiSmile.model.MedicalRecordSectionModel;
import edu.mx.unsis.unsiSmile.model.PatientMedicalRecordModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.EMedicalRecords;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IMedicalRecordCatalogRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IPatientMedicalRecordRepository;
import edu.mx.unsis.unsiSmile.service.patients.PatientService;
import io.jsonwebtoken.lang.Assert;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordCatalogService {
    private final IMedicalRecordCatalogRepository medicalRecordCatalogRepository;
    private final MedicalRecordCatalogMapper medicalRecordCatalogMapper;
    private final MedicalRecordSectionService medicalRecordSectionService;
    private final FormSectionService formSectionService;
    private final PatientMedicalRecordService patientMedicalRecordService;
    private final PatientService patientService;
    private final IPatientMedicalRecordRepository patientMedicalRecordRepository;

    @Transactional
    public void save(MedicalRecordCatalogRequest request) {
        try {
            Assert.notNull(request, ResponseMessages.REQUEST_CANNOT_BE_NULL);

            MedicalRecordCatalogModel medicalRecordCatalogModel = medicalRecordCatalogMapper.toEntity(request);

            MedicalRecordCatalogModel savedCatalog = medicalRecordCatalogRepository.save(medicalRecordCatalogModel);

        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_MEDICAL_RECORD, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public MedicalRecordCatalogResponse findById(Long idPatientMedicalRecord, String idPatient) {
        try {
            Assert.notNull(idPatientMedicalRecord, ResponseMessages.MEDICAL_RECORD_ID_CANNOT_BE_NULL);
            if (idPatientMedicalRecord == 0) {
                throw new AppException(ResponseMessages.MEDICAL_RECORD_ID_CANNOT_BE_ZERO, HttpStatus.BAD_REQUEST);
            }

            Assert.notNull(idPatient, ResponseMessages.PATIENT_ID_CANNOT_BE_NULL);
            if ("0".equals(idPatient)) {
                throw new AppException(ResponseMessages.PATIENT_ID_CANNOT_BE_ZERO, HttpStatus.BAD_REQUEST);
            }

            patientService.getPatientById(idPatient);

            PatientMedicalRecordModel patientMedicalRecord = patientMedicalRecordService.findByPatientAndMedicalRecord(idPatient, idPatientMedicalRecord);

            return this.toResponse(patientMedicalRecord);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_MEDICAL_RECORD, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<MedicalRecordCatalogResponse> findAll() {
        try {
            List<MedicalRecordCatalogModel> medicalRecordCatalogList = medicalRecordCatalogRepository.findAll();

            return medicalRecordCatalogList.stream()
                    .map(medicalRecordCatalogMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_MEDICAL_RECORD_CATALOG, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<MedicalRecordCatalogModel> catalogOptional = medicalRecordCatalogRepository.findById(id);

            catalogOptional.ifPresentOrElse(
                    catalog -> {
                      catalog.setStatusKey(Constants.INACTIVE);
                      medicalRecordCatalogRepository.save(catalog);
                    },
                    () -> {
                        throw new AppException(String.format(ResponseMessages.CLINICAL_HISTORY_CATALOG_NOT_FOUND, id),
                                HttpStatus.NOT_FOUND);
                    }
            );
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(String.format(ResponseMessages.FAILED_DELETE_MEDICAL_RECORD_CATALOG, id),
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private MedicalRecordCatalogResponse toResponse(PatientMedicalRecordModel patientMedicalRecord) {

        List<MedicalRecordSectionModel> medicalRecordSectionList = medicalRecordSectionService
                .findByMedicalRecordId(patientMedicalRecord.getMedicalRecordCatalog().getIdMedicalRecordCatalog());

        List<FormSectionResponse> sections = formSectionService.findAllByMedicalRecord(medicalRecordSectionList, patientMedicalRecord.getPatient().getIdPatient(), patientMedicalRecord.getIdPatientMedicalRecord());

        MedicalRecordCatalogResponse medicalRecordCatalogResponse = medicalRecordCatalogMapper.toDto(patientMedicalRecord.getMedicalRecordCatalog());

        medicalRecordCatalogResponse.setMedicalRecordNumber(patientMedicalRecord.getPatient().getMedicalRecordNumber());
        medicalRecordCatalogResponse.setAppointmentDate(patientMedicalRecord.getAppointmentDate());

        medicalRecordCatalogResponse.setFormSections(sections);
        medicalRecordCatalogResponse.setIdPatientMedicalRecord(patientMedicalRecord.getIdPatientMedicalRecord());

        return medicalRecordCatalogResponse;
    }

    @Transactional(readOnly = true)
    public List<PatientMedicalRecordResponse> searchMedicalRecords(String idPatient) {
        try {
            patientService.getPatientById(idPatient);

            List<Object[]> results = medicalRecordCatalogRepository.findAllMedicalRecordByPatientId(idPatient);
            return results.stream()
                    .map(this::mapToMedicalRecordResponse)
                    .collect(Collectors.toList());
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_PATIENT_MEDICAL_RECORDS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public MedicalRecordCatalogResponse searchGeneralMedicalRecord(@NonNull String idPatient) {
        try {
            patientService.getPatientById(idPatient);
            PatientMedicalRecordModel patientMedicalRecord = patientMedicalRecordService.findGeneralMedicalRecordByPatientId(idPatient);
            return this.toResponse(patientMedicalRecord);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_GENERAL_MEDICAL_RECORD, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public MedicalRecordCatalogResponse createNewGeneralMedicalRecord(@NonNull String idPatient) {
        try {
            patientService.getPatientById(idPatient);
            PatientMedicalRecordModel patientMedicalRecord = patientMedicalRecordService.createNewGeneralMedicalRecord(idPatient);
            return this.toResponse(patientMedicalRecord);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Failed to search Medical Record", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private PatientMedicalRecordResponse mapToMedicalRecordResponse(Object[] result) {
        return PatientMedicalRecordResponse.builder()
                .id(((Number) result[0]).longValue())
                .medicalRecordName((String) result[1])
                .patientMedicalRecordId(result[2] != null ? ((Number) result[2]).longValue() : 0L)
                .patientId(result[3] != null ? result[3].toString() : null)
                .build();
    }

    @Transactional(readOnly = true)
    public MedicalRecordCatalogResponse findByMedicalRecordAndPatient(EMedicalRecords medicalRecord, String idPatient) {
        try {
            Assert.notNull(idPatient, ResponseMessages.PATIENT_ID_CANNOT_BE_NULL);

            if ("0".equals(idPatient)) {
                throw new AppException(ResponseMessages.PATIENT_ID_CANNOT_BE_ZERO, HttpStatus.BAD_REQUEST);
            }

            patientService.getPatientById(idPatient);

            PatientMedicalRecordModel patientMedicalRecord =
                    patientMedicalRecordRepository.findFirstByPatient_IdPatientAndMedicalRecordCatalog_MedicalRecordNameOrderByCreatedAtDesc(
                            idPatient, medicalRecord.getDescription());

            if (patientMedicalRecord == null) {
                throw new AppException(
                        String.format(ResponseMessages.MEDICAL_RECORD_NOT_FOUND, medicalRecord.getDescription(),
                                idPatient), HttpStatus.NOT_FOUND);
            }

            return this.toResponse(patientMedicalRecord);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.ERROR_FETCHING_MEDICAL_RECORD,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
