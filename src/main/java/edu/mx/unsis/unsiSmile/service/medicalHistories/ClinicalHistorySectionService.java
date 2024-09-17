package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.ClinicalHistorySectionModel;

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
    public ClinicalHistorySectionModel save(ClinicalHistorySectionModel sectionModel) {
        try {
            return clinicalHistorySectionRepository.save(sectionModel);
        } catch (Exception ex) {
            throw new AppException("Failed to save clinical history section", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public ClinicalHistorySectionModel findById(Long id) {
        try {
            return clinicalHistorySectionRepository.findById(id)
                    .orElseThrow(() -> new AppException("Clinical history section not found with ID: " + id, HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new AppException("Failed to find clinical history section with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
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
    public void deleteById(Long id) {
        try {
            Optional<ClinicalHistorySectionModel> sectionOptional = clinicalHistorySectionRepository.findById(id);
            sectionOptional.ifPresentOrElse(
                    section -> {
                        section.setStatusKey(Constants.INACTIVE);
                        clinicalHistorySectionRepository.save(section);
                    },
                    () -> {
                        throw new AppException("Clinical history section not found with ID: " + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete clinical history section with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
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
}
