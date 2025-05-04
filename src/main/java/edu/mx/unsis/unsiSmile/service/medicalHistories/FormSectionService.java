package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FormSectionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.FormSectionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.QuestionResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.FormSectionMapper;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.ReviewStatusMapper;
import edu.mx.unsis.unsiSmile.model.ClinicalHistorySectionModel;
import edu.mx.unsis.unsiSmile.model.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.PatientClinicalHistoryModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatusModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IFormSectionRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IPatientClinicalHistoryRepository;
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
    private final IPatientClinicalHistoryRepository patientClinicalHistoryRepository;
    private final FormSectionMapper formSectionMapper;
    private final QuestionService questionService;
    private final ReviewStatusService reviewStatusService;
    private final ReviewStatusMapper reviewStatusMapper;

    @Transactional
    public void save(FormSectionRequest request) {
        try {
            Assert.notNull(request, ResponseMessages.FORM_SECTION_REQUEST_NULL);

            FormSectionModel formSectionModel = formSectionMapper.toEntity(request);

            formSectionRepository.save(formSectionModel);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_SAVE_FORM_SECTION, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public FormSectionResponse findById(Long id, Long idPatientClinicalHistory) {
        try {
            validateIds(id, idPatientClinicalHistory);

            FormSectionModel formSectionModel = findFormSectionById(id);
            PatientClinicalHistoryModel patientClinicalHistoryModel = findPatientClinicalHistoryById(idPatientClinicalHistory);

            FormSectionResponse response = buildFormSectionResponse(formSectionModel, patientClinicalHistoryModel);
            setStatusInResponse(response, formSectionModel, idPatientClinicalHistory, id);

            return response;
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FIND_FORM_SECTION + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private void validateIds(Long id, Long idPatientClinicalHistory) {
        Assert.notNull(id, ResponseMessages.FORM_SECTION_ID_NULL);
        Assert.notNull(idPatientClinicalHistory, ResponseMessages.PATIENT_CLINICAL_HISTORY_ID_NULL);
    }

    private FormSectionModel findFormSectionById(Long id) {
        return formSectionRepository.findById(id)
                .orElseThrow(() -> new AppException(ResponseMessages.FORM_SECTION_NOT_FOUND + id, HttpStatus.NOT_FOUND));
    }

    private PatientClinicalHistoryModel findPatientClinicalHistoryById(Long idPatientClinicalHistory) {
        return patientClinicalHistoryRepository.findById(idPatientClinicalHistory)
                .orElseThrow(() -> new AppException(
                        String.format(ResponseMessages.PATIENT_CLINICAL_HISTORY_NOT_FOUND, idPatientClinicalHistory),
                        HttpStatus.NOT_FOUND));
    }

    private FormSectionResponse buildFormSectionResponse(FormSectionModel formSectionModel, PatientClinicalHistoryModel patientClinicalHistoryModel) {
        return this.toResponse(formSectionModel,
                patientClinicalHistoryModel.getPatient().getIdPatient(),
                patientClinicalHistoryModel.getIdPatientClinicalHistory());
    }

    private void setStatusInResponse(FormSectionResponse response, FormSectionModel formSectionModel,
                                     Long idPatientClinicalHistory, Long idFormSection) {
        ReviewStatusModel status = formSectionModel.getRequiresReview()
                ? reviewStatusService.getStatusModelByPatientMedicalRecordId(idPatientClinicalHistory, idFormSection)
                : null;

        response.setReviewStatus(reviewStatusService.buildStatusResponse(status, formSectionModel.getRequiresReview()));
    }

    @Transactional(readOnly = true)
    public List<FormSectionResponse> findAll() {
        try {
            List<FormSectionModel> formSectionModelList = formSectionRepository.findAll();

            if (formSectionModelList.isEmpty()) {
                throw new AppException(ResponseMessages.NO_FORM_SECTIONS, HttpStatus.NOT_FOUND);
            } else {
                return formSectionModelList.stream()
                        .map(formSectionMapper::toDto)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_FORM_SECTIONS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
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
                        throw new AppException(ResponseMessages.FORM_SECTION_NOT_FOUND + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_FORM_SECTION + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
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
                    .map(sectionModel -> buildFormSectionResponseWithStatus(sectionModel, patientId, patientClinicalHistoryId))
                    .collect(Collectors.toList());
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_FORM_SECTIONS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private FormSectionResponse buildFormSectionResponseWithStatus(FormSectionModel sectionModel,
                                                                   String patientId,
                                                                   Long patientClinicalHistoryId) {
        FormSectionResponse response = toResponse(sectionModel, patientId, patientClinicalHistoryId);

        ReviewStatusModel status = sectionModel.getRequiresReview()
                ? reviewStatusService.getStatusByPatientClinicalHistoryIdAndSection(patientId, sectionModel.getIdFormSection())
                : null;

        response.setReviewStatus(reviewStatusService.buildStatusResponse(status, sectionModel.getRequiresReview()));
        return response;
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
            throw new AppException(ResponseMessages.FAILED_FETCH_SUBFORM, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
