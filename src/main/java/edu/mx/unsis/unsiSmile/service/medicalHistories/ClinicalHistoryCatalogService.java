package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ClinicalHistoryCatalogRequest;
import edu.mx.unsis.unsiSmile.dtos.response.FormSectionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ClinicalHistoryCatalogResponse;
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
    public ClinicalHistoryCatalogResponse save(ClinicalHistoryCatalogRequest request) {
        try {
            Assert.notNull(request, "ClinicalHistoryCatalogRequest cannot be null");

            ClinicalHistoryCatalogModel clinicalHistoryCatalogModel = clinicalHistoryCatalogMapper.toEntity(request);

            ClinicalHistoryCatalogModel savedCatalog = clinicalHistoryCatalogRepository.save(clinicalHistoryCatalogModel);

            return clinicalHistoryCatalogMapper.toDto(savedCatalog);
        } catch (Exception ex) {
            throw new AppException("Failed to save clinical history catalog due to an internal server error", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public ClinicalHistoryCatalogResponse findById(Long id) {
        try {
            Assert.notNull(id, "Id cannot be null");

            ClinicalHistoryCatalogModel clinicalHistoryCatalogModel = clinicalHistoryCatalogRepository.findById(id)
                    .orElseThrow(() -> new AppException("Clinical history catalog not found with id: " + id, HttpStatus.NOT_FOUND));

            return this.toResponse(clinicalHistoryCatalogModel);
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

    private ClinicalHistoryCatalogResponse toResponse(ClinicalHistoryCatalogModel catalog) {

        List<ClinicalHistorySectionModel> clinicalHistorySectionList = clinicalHistorySectionService
                .findByClinicalHistoryId(catalog.getIdClinicalHistoryCatalog());

        List<FormSectionResponse> sections = formSectionService.findAllByClinicalHistory(clinicalHistorySectionList);

        ClinicalHistoryCatalogResponse clinicalHistoryCatalogResponse = clinicalHistoryCatalogMapper.toDto(catalog);

        clinicalHistoryCatalogResponse.setFormSections(sections);

        return clinicalHistoryCatalogResponse;
    }
}
