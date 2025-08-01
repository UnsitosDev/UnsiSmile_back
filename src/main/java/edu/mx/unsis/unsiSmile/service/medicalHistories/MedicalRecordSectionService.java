package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.forms.sections.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.forms.catalogs.MedicalRecordCatalogModel;
import edu.mx.unsis.unsiSmile.model.forms.sections.MedicalRecordSectionModel;
import edu.mx.unsis.unsiSmile.model.utils.MedicalRecordSectionModelPk;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IMedicalRecordSectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicalRecordSectionService {

    private final IMedicalRecordSectionRepository medicalRecordSectionRepository;

    @Transactional
    public MedicalRecordSectionModel save(Long idMedicalRecordCatalog, String idSection) {
        try {
            return medicalRecordSectionRepository.save(toEntity(idMedicalRecordCatalog, idSection));
        } catch (Exception ex) {
            throw new AppException("Failed to save clinical history section", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public MedicalRecordSectionModel findById(MedicalRecordSectionModelPk medicalRecordSectionModelPk) {
        try {
            return medicalRecordSectionRepository.findById(medicalRecordSectionModelPk)
                    .orElseThrow(() -> new AppException("Clinical history section not found with ID: " + medicalRecordSectionModelPk, HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new AppException("Failed to find clinical history section with ID: " + medicalRecordSectionModelPk, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<MedicalRecordSectionModel> findAll() {
        try {
            List<MedicalRecordSectionModel> sectionList = medicalRecordSectionRepository.findAll();
            if (sectionList.isEmpty()) {
                throw new AppException("No clinical history sections found", HttpStatus.NOT_FOUND);
            }
            return sectionList;
        } catch (Exception ex) {
            throw new AppException("Failed to fetch clinical history sections", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(MedicalRecordSectionModelPk medicalRecordSectionModelPk) {
        try {
            Optional<MedicalRecordSectionModel> sectionOptional = medicalRecordSectionRepository.findById(medicalRecordSectionModelPk);
            sectionOptional.ifPresentOrElse(
                    section -> {
                        section.setStatusKey(Constants.INACTIVE);
                        medicalRecordSectionRepository.save(section);
                    },
                    () -> {
                        throw new AppException("Clinical history section not found with ID: " + medicalRecordSectionModelPk, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete clinical history section with ID: " + medicalRecordSectionModelPk, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public List<MedicalRecordSectionModel> findByMedicalRecordId(Long medicalRecordId) {
        try {
            return medicalRecordSectionRepository.findAllByMedicalRecordId(medicalRecordId);
        } catch (Exception ex){
            throw new AppException("Failed to fetch clinical history sections", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private MedicalRecordSectionModel toEntity(Long idMedicalRecordCatalog, String idSection) {
        return MedicalRecordSectionModel.builder()
                .medicalRecordCatalogModel(MedicalRecordCatalogModel.builder()
                        .idMedicalRecordCatalog(idMedicalRecordCatalog)
                        .build())
                .formSectionModel(FormSectionModel.builder()
                        .idFormSection(idSection)
                        .build())
                .build();
    }
}
