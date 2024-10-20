package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ClinicalHistoryCatalogRequest;
import edu.mx.unsis.unsiSmile.dtos.response.FormSectionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ClinicalHistoryCatalogResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PatientClinicalHistoryResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.ClinicalHistoryCatalogMapper;
import edu.mx.unsis.unsiSmile.model.ClinicalHistoryCatalogModel;
import edu.mx.unsis.unsiSmile.model.ClinicalHistorySectionModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IClinicalHistoryCatalogRepository;
import io.jsonwebtoken.lang.Assert;
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
    public ClinicalHistoryCatalogResponse findById(Long id, Long patientClinicalHistoryId) {
        try {
            Assert.notNull(id, "Clinical History Id cannot be null");
            if (id == 0) {
                throw new AppException("Clinical History Id cannot be 0", HttpStatus.BAD_REQUEST);
            }

            Assert.notNull(patientClinicalHistoryId, "Patient clinical history ID cannot be null");
            if (patientClinicalHistoryId == 0) {
                throw new AppException("Patient clinical history ID cannot be 0", HttpStatus.BAD_REQUEST);
            }

            ClinicalHistoryCatalogModel clinicalHistoryCatalogModel = clinicalHistoryCatalogRepository.findById(id)
                    .orElseThrow(() -> new AppException("Clinical history catalog not found with id: " + id, HttpStatus.NOT_FOUND));

            return this.toResponse(clinicalHistoryCatalogModel, patientClinicalHistoryId);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to find clinical history catalog with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
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

    private ClinicalHistoryCatalogResponse toResponse(ClinicalHistoryCatalogModel catalog, Long patientClinicalHistoryId) {

        List<ClinicalHistorySectionModel> clinicalHistorySectionList = clinicalHistorySectionService
                .findByClinicalHistoryId(catalog.getIdClinicalHistoryCatalog());

        List<FormSectionResponse> sections = formSectionService.findAllByClinicalHistory(clinicalHistorySectionList, patientClinicalHistoryId);

        ClinicalHistoryCatalogResponse clinicalHistoryCatalogResponse = clinicalHistoryCatalogMapper.toDto(catalog);

        clinicalHistoryCatalogResponse.setFormSections(sections);

        return clinicalHistoryCatalogResponse;
    }

    @Transactional(readOnly = true)
    public List<PatientClinicalHistoryResponse> searchClinicalHistory(Long idPatient) {//falta validar que primero exista el paciente, si no devolver un error
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

    private PatientClinicalHistoryResponse mapToClinicalHistoryResponse(Object[] result) {
        return PatientClinicalHistoryResponse.builder()
                .id(((Number) result[0]).longValue())
                .clinicalHistoryName((String) result[1])
                .patientClinicalHistoryId(result[2] != null ? ((Number) result[2]).longValue() : 0L)
                .patientId(result[3] != null ? ((Number) result[3]).longValue() : 0L)
                .build();
    }
}
