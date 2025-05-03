package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ClinicalHistoryCatalogRequest;
import edu.mx.unsis.unsiSmile.dtos.response.FormSectionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ClinicalHistoryCatalogResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PatientClinicalHistoryResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.ClinicalHistoryCatalogMapper;
import edu.mx.unsis.unsiSmile.model.ClinicalHistoryCatalogModel;
import edu.mx.unsis.unsiSmile.model.ClinicalHistorySectionModel;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IClinicalHistoryCatalogRepository;
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
public class ClinicalHistoryCatalogService {
    private final IClinicalHistoryCatalogRepository clinicalHistoryCatalogRepository;
    private final ClinicalHistoryCatalogMapper clinicalHistoryCatalogMapper;
    private final ClinicalHistorySectionService clinicalHistorySectionService;
    private final FormSectionService formSectionService;
    private final PatientClinicalHistoryService patientClinicalHistoryService;
    private final PatientService patientService;

    @Transactional
    public void save(ClinicalHistoryCatalogRequest request) {
        try {
            Assert.notNull(request, "ClinicalHistoryCatalogRequest cannot be null");

            ClinicalHistoryCatalogModel clinicalHistoryCatalogModel = clinicalHistoryCatalogMapper.toEntity(request);

            ClinicalHistoryCatalogModel savedCatalog = clinicalHistoryCatalogRepository.save(clinicalHistoryCatalogModel);

        } catch (Exception ex) {
            throw new AppException("Failed to save clinical history catalog due to an internal server error", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public ClinicalHistoryCatalogResponse findById(Long idPatientMedicalRecord, String idPatient) {
        try {
            Assert.notNull(idPatientMedicalRecord, ResponseMessages.MEDICAL_RECORD_ID_CANNOT_BE_NULL);
            if (idPatientMedicalRecord == 0) {
                throw new AppException(ResponseMessages.MEDICAL_RECORD_ID_CANNOT_BE_ZERO, HttpStatus.BAD_REQUEST);
            }

            Assert.notNull(idPatient, ResponseMessages.PATIENT_ID_CANNOT_BE_NULL);
            if ("0".equals(idPatient)) {
                throw new AppException(ResponseMessages.PATIENT_ID_CANNOT_BE_ZERO, HttpStatus.BAD_REQUEST);
            }

            PatientClinicalHistoryModel patientClinicalHistory = patientClinicalHistoryService.findByPatientAndClinicalHistory(idPatient, idPatientMedicalRecord);

            return this.toResponse(patientClinicalHistory);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_MEDICAL_RECORD, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<ClinicalHistoryCatalogResponse> findAll() {
        try {
            List<ClinicalHistoryCatalogModel> clinicalHistoryCatalogList = clinicalHistoryCatalogRepository.findAll();

            if (clinicalHistoryCatalogList.isEmpty()) {
                throw new AppException("No clinical history catalogs found", HttpStatus.NOT_FOUND);
            } else {
                return clinicalHistoryCatalogList.stream()
                        .map(clinicalHistoryCatalogMapper::toDto)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            throw new AppException("Failed to fetch clinical history catalogs", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<ClinicalHistoryCatalogModel> catalogOptional = clinicalHistoryCatalogRepository.findById(id);

            catalogOptional.ifPresentOrElse(
                    catalog -> {
                      catalog.setStatusKey(Constants.INACTIVE);
                      clinicalHistoryCatalogRepository.save(catalog);
                    },
                    () -> {
                        throw new AppException("Clinical history catalog not found with ID: " + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete clinical history catalog with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private ClinicalHistoryCatalogResponse toResponse(PatientClinicalHistoryModel patientClinicalHistory) {

        List<ClinicalHistorySectionModel> clinicalHistorySectionList = clinicalHistorySectionService
                .findByClinicalHistoryId(patientClinicalHistory.getClinicalHistoryCatalog().getIdClinicalHistoryCatalog());

        List<FormSectionResponse> sections = formSectionService.findAllByClinicalHistory(clinicalHistorySectionList, patientClinicalHistory.getPatient().getIdPatient(), patientClinicalHistory.getIdPatientClinicalHistory());

        ClinicalHistoryCatalogResponse clinicalHistoryCatalogResponse = clinicalHistoryCatalogMapper.toDto(patientClinicalHistory.getClinicalHistoryCatalog());

        clinicalHistoryCatalogResponse.setMedicalRecordNumber(patientClinicalHistory.getPatient().getMedicalRecordNumber());
        clinicalHistoryCatalogResponse.setAppointmentDate(patientClinicalHistory.getAppointmentDate());

        clinicalHistoryCatalogResponse.setFormSections(sections);

        return clinicalHistoryCatalogResponse;
    }

    @Transactional(readOnly = true)
    public List<PatientClinicalHistoryResponse> searchClinicalHistory(String idPatient) {//falta validar que primero exista el paciente, si no devolver un error
        try {
            List<Object[]> results = clinicalHistoryCatalogRepository.findAllClinicalHistoryByPatientId(idPatient);
            if (results.isEmpty()) {
                throw new AppException("No clinical history found for patient with ID: " + idPatient, HttpStatus.NOT_FOUND);
            }
            return results.stream()
                    .map(this::mapToClinicalHistoryResponse)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to search clinical history", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public ClinicalHistoryCatalogResponse searchGeneralMedicalRecord(@NonNull String idPatient) {
        try {
            patientService.getPatientById(idPatient);
            PatientClinicalHistoryModel patientClinicalHistory = patientClinicalHistoryService.findGeneralMedicalRecordByPatientId(idPatient);
            return this.toResponse(patientClinicalHistory);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Failed to search clinical history", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public ClinicalHistoryCatalogResponse createNewGeneralMedicalRecord(@NonNull String idPatient) {
        try {
            patientService.getPatientById(idPatient);
            PatientClinicalHistoryModel patientClinicalHistory = patientClinicalHistoryService.createNewGeneralMedicalRecord(idPatient);
            return this.toResponse(patientClinicalHistory);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Failed to search clinical history", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private PatientClinicalHistoryResponse mapToClinicalHistoryResponse(Object[] result) {
        return PatientClinicalHistoryResponse.builder()
                .id(((Number) result[0]).longValue())
                .clinicalHistoryName((String) result[1])
                .patientClinicalHistoryId(result[2] != null ? ((Number) result[2]).longValue() : 0L)
                .patientId(result[3] != null ? result[3].toString() : null)
                .build();
    }
}
