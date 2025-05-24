package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.ClinicalHistoryCatalogModel;
import edu.mx.unsis.unsiSmile.model.ClinicalHistorySectionModel;
import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.utils.ClinicalHistorySectionModelPk;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IClinicalHistorySectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClinicalHistorySectionService {

    private final IClinicalHistorySectionRepository clinicalHistorySectionRepository;

    @Transactional
    public ClinicalHistorySectionModel save(Long idClinicalHistory, String idSection) {
        try {
            return clinicalHistorySectionRepository.save(toEntity(idClinicalHistory, idSection));
        } catch (Exception ex) {
            throw new AppException("Failed to save clinical history section", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public ClinicalHistorySectionModel findById(ClinicalHistorySectionModelPk  clinicalHistorySectionModelPk) {
        try {
            return clinicalHistorySectionRepository.findById(clinicalHistorySectionModelPk)
                    .orElseThrow(() -> new AppException("Clinical history section not found with ID: " + clinicalHistorySectionModelPk, HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new AppException("Failed to find clinical history section with ID: " + clinicalHistorySectionModelPk, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<ClinicalHistorySectionModel> findAll() {
        try {
            List<ClinicalHistorySectionModel> sectionList = clinicalHistorySectionRepository.findAll();
            if (sectionList.isEmpty()) {
                throw new AppException("No clinical history sections found", HttpStatus.NOT_FOUND);
            }
            return sectionList;
        } catch (Exception ex) {
            throw new AppException("Failed to fetch clinical history sections", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(ClinicalHistorySectionModelPk clinicalHistorySectionModelPk) {
        try {
            Optional<ClinicalHistorySectionModel> sectionOptional = clinicalHistorySectionRepository.findById(clinicalHistorySectionModelPk);
            sectionOptional.ifPresentOrElse(
                    section -> {
                        section.setStatusKey(Constants.INACTIVE);
                        clinicalHistorySectionRepository.save(section);
                    },
                    () -> {
                        throw new AppException("Clinical history section not found with ID: " + clinicalHistorySectionModelPk, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete clinical history section with ID: " + clinicalHistorySectionModelPk, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public List<ClinicalHistorySectionModel> findByClinicalHistoryId(Long clinicalHistoryId) {
        try {
            return clinicalHistorySectionRepository.findAllByClinicalHistoryId(clinicalHistoryId);
        } catch (Exception ex){
            throw new AppException("Failed to fetch clinical history sections", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private ClinicalHistorySectionModel toEntity(Long idClinicalHistory, String idSection) {
        return ClinicalHistorySectionModel.builder()
                .clinicalHistoryCatalogModel(ClinicalHistoryCatalogModel.builder()
                        .idClinicalHistoryCatalog(idClinicalHistory)
                        .build())
                .formSectionModel(FormSectionModel.builder()
                        .idFormSection(idSection)
                        .build())
                .build();
    }
}
