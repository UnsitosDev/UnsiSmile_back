package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FormSectionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.FormSectionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.QuestionResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.FormSectionMapper;
import edu.mx.unsis.unsiSmile.model.ClinicalHistorySectionModel;
import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IFormSectionRepository;
import edu.mx.unsis.unsiSmile.service.QuestionService;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormSectionService {

    private final IFormSectionRepository formSectionRepository;
    private final FormSectionMapper formSectionMapper;
    private final QuestionService questionService;

    @Transactional
    public void save(FormSectionRequest request) {
        try {
            Assert.notNull(request, "FormSectionRequest cannot be null");

            FormSectionModel formSectionModel = formSectionMapper.toEntity(request);

            formSectionRepository.save(formSectionModel);
        } catch (Exception ex) {
            throw new AppException("Failed to save form section due to an internal server error", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public FormSectionResponse findById(Long id, String patientId) {
        try {
            Assert.notNull(id, "Id cannot be null");

            FormSectionModel formSectionModel = formSectionRepository.findById(id)
                    .orElseThrow(() -> new AppException("Form section not found with id: " + id, HttpStatus.NOT_FOUND));

            return this.toResponse(formSectionModel, patientId, null);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to find form section with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<FormSectionResponse> findAll() {
        try {
            List<FormSectionModel> formSectionModelList = formSectionRepository.findAll();

            if (formSectionModelList.isEmpty()) {
                throw new AppException("No form sections found", HttpStatus.NOT_FOUND);
            } else {
                return formSectionModelList.stream()
                        .map(formSectionMapper::toDto)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            throw new AppException("Failed to fetch form sections", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<FormSectionModel> formSectionOptional = formSectionRepository.findById(id);

            formSectionOptional.ifPresentOrElse(
                    formSection -> {
                        formSection.setStatusKey(Constants.INACTIVE);
                        formSectionRepository.save(formSection);
                    },
                    () -> {
                        throw new AppException("Form section not found with ID: " + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete form section with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<FormSectionResponse> findAllByClinicalHistory(
            List<ClinicalHistorySectionModel> clinicalHistorySectionModels, String patientId, Long patientClinicalHistoryId) {
        try {
            Set<Long> sectionIds = clinicalHistorySectionModels.stream()
                    .map(chsm -> chsm.getFormSectionModel().getIdFormSection())
                    .collect(Collectors.toSet());

            List<FormSectionModel> formSectionModels = formSectionRepository.findAllById(sectionIds);

            return formSectionModels.stream()
                    .map(sectionModel -> toResponse(sectionModel, patientId, patientClinicalHistoryId))
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch form sections", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public FormSectionResponse toResponse(FormSectionModel sectionModel, String patientId, Long patientClinicalHistoryId) {
        FormSectionResponse formSectionResponse = formSectionMapper.toDto(sectionModel);
        List<QuestionResponse> questions = questionService.findAllBySection(sectionModel.getIdFormSection(), 
        patientId, (sectionModel.getIdFormSection() == 1) ? patientClinicalHistoryId : 0L);

        formSectionResponse.setQuestions(questions);

        boolean hasAnsweredQuestions = questions.stream().anyMatch(question -> question.getAnswer() != null);
        formSectionResponse.setIsAnswered(hasAnsweredQuestions);

        List<FormSectionModel> subSections = getSubFormSectionModel(sectionModel.getIdFormSection());
        if (subSections != null && !subSections.isEmpty()) {
            List<FormSectionResponse> subSectionResponses = subSections.stream()
                    .map(subSection -> toResponse(subSection, patientId, patientClinicalHistoryId))
                    .collect(Collectors.toList());
            formSectionResponse.setSubSections(subSectionResponses);
        }

        return formSectionResponse;
    }

    private List<FormSectionModel> getSubFormSectionModel(Long parentSectionModelId) {
        try {
            return formSectionRepository.findByParenSectionId(parentSectionModelId);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch subform section model", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
