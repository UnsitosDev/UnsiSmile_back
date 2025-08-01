package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.forms.sections.FormSectionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.forms.sections.FormSectionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.forms.questions.QuestionResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.forms.sections.FormSectionMapper;
import edu.mx.unsis.unsiSmile.mappers.forms.sections.ReviewStatusMapper;
import edu.mx.unsis.unsiSmile.model.forms.sections.FormSectionModel;
import edu.mx.unsis.unsiSmile.model.forms.sections.MedicalRecordSectionModel;
import edu.mx.unsis.unsiSmile.model.patients.PatientMedicalRecordModel;
import edu.mx.unsis.unsiSmile.model.forms.sections.ReviewStatusModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IFormSectionRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IPatientMedicalRecordRepository;
import edu.mx.unsis.unsiSmile.service.QuestionService;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormSectionService {

    private final IFormSectionRepository formSectionRepository;
    private final IPatientMedicalRecordRepository patientMedicalRecordRepository;
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
    public FormSectionResponse findById(String id, Long idPatientMedicalRecord) {
        try {
            validateIds(id, idPatientMedicalRecord);

            FormSectionModel formSectionModel = findFormSectionById(id);
            PatientMedicalRecordModel patientMedicalRecordModel = findPatientMedicalRecordById(idPatientMedicalRecord);

            FormSectionResponse response = buildFormSectionResponse(formSectionModel, patientMedicalRecordModel);
            setStatusInResponse(response, formSectionModel, idPatientMedicalRecord, id);

            return response;
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FIND_FORM_SECTION + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private void validateIds(String id, Long idPatientMedicalRecord) {
        Assert.notNull(id, ResponseMessages.FORM_SECTION_ID_NULL);
        Assert.notNull(idPatientMedicalRecord, ResponseMessages.PATIENT_CLINICAL_HISTORY_ID_NULL);
    }

    private FormSectionModel findFormSectionById(String id) {
        return formSectionRepository.findById(id)
                .orElseThrow(() -> new AppException(ResponseMessages.FORM_SECTION_NOT_FOUND + id, HttpStatus.NOT_FOUND));
    }

    private PatientMedicalRecordModel findPatientMedicalRecordById(Long idPatientMedicalRecord) {
        return patientMedicalRecordRepository.findById(idPatientMedicalRecord)
                .orElseThrow(() -> new AppException(
                        String.format(ResponseMessages.PATIENT_CLINICAL_HISTORY_NOT_FOUND, idPatientMedicalRecord),
                        HttpStatus.NOT_FOUND));
    }

    private FormSectionResponse buildFormSectionResponse(FormSectionModel formSectionModel, PatientMedicalRecordModel patientMedicalRecordModel) {
        return this.toResponse(formSectionModel,
                patientMedicalRecordModel.getPatient().getIdPatient(),
                patientMedicalRecordModel.getIdPatientMedicalRecord());
    }

    private void setStatusInResponse(FormSectionResponse response, FormSectionModel formSectionModel,
                                     Long idPatientMedicalRecord, String idFormSection) {
        ReviewStatusModel status = formSectionModel.getRequiresReview()
                ? reviewStatusService.getStatusModelByPatientMedicalRecordId(idPatientMedicalRecord, idFormSection)
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
    public void deleteById(String id) {
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
    public List<FormSectionResponse> findAllByMedicalRecord(
            List<MedicalRecordSectionModel> medicalRecordSectionModels, String patientId, Long patientMedicalRecordId) {
        try {
            Map<String, Long> sectionOrderMap = medicalRecordSectionModels.stream()
                    .collect(Collectors.toMap(
                            mrsm -> mrsm.getFormSectionModel().getIdFormSection(),
                            MedicalRecordSectionModel::getOrder
                    ));

            Set<String> sectionIds = sectionOrderMap.keySet();

            List<FormSectionModel> formSectionModels = formSectionRepository.findAllById(sectionIds);

            return formSectionModels.stream()
                    .map(sectionModel -> buildFormSectionResponseWithStatus(
                            sectionModel,
                            patientId,
                            patientMedicalRecordId,
                            sectionOrderMap.get(sectionModel.getIdFormSection())))
                    .sorted(Comparator.comparingLong(FormSectionResponse::getSectionOrder))
                    .collect(Collectors.toList());
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_FORM_SECTIONS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private FormSectionResponse buildFormSectionResponseWithStatus(FormSectionModel sectionModel,
                                                                   String patientId,
                                                                   Long patientMedicalRecordId,
                                                                   Long sectionOrder) {
        FormSectionResponse response = toResponse(sectionModel, patientId, patientMedicalRecordId);
        response.setSectionOrder(sectionOrder);

        ReviewStatusModel status = sectionModel.getRequiresReview()
                ? reviewStatusService.getStatusByPatientMedicalRecordIdAndSection(patientId, sectionModel.getIdFormSection())
                : null;

        response.setReviewStatus(reviewStatusService.buildStatusResponse(status, sectionModel.getRequiresReview()));
        return response;
    }

    @Transactional(readOnly = true)
    public FormSectionResponse toResponse(FormSectionModel sectionModel, String patientId, Long patientMedicalRecordId) {
        FormSectionResponse formSectionResponse = formSectionMapper.toDto(sectionModel);
        List<QuestionResponse> questions = questionService.findAllBySection(sectionModel.getIdFormSection(),
        patientId, ("SV-01".equals(sectionModel.getIdFormSection()) ? patientMedicalRecordId : 0L));

        formSectionResponse.setQuestions(questions);

        boolean hasAnsweredQuestions = questions.stream().anyMatch(question -> question.getAnswer() != null);
        formSectionResponse.setIsAnswered(hasAnsweredQuestions);

        List<FormSectionModel> subSections = getSubFormSectionModel(sectionModel.getIdFormSection());
        if (subSections != null && !subSections.isEmpty()) {
            List<FormSectionResponse> subSectionResponses = subSections.stream()
                    .map(subSection -> toResponse(subSection, patientId, patientMedicalRecordId))
                    .collect(Collectors.toList());
            formSectionResponse.setSubSections(subSectionResponses);
        }

        return formSectionResponse;
    }

    private List<FormSectionModel> getSubFormSectionModel(String parentSectionModelId) {
        try {
            return formSectionRepository.findByParenSectionId(parentSectionModelId);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_SUBFORM, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
