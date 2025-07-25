package edu.mx.unsis.unsiSmile.service;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.QuestionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.*;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.QuestionMapper;
import edu.mx.unsis.unsiSmile.model.QuestionModel;
import edu.mx.unsis.unsiSmile.model.QuestionValidationModel;
import edu.mx.unsis.unsiSmile.repository.IQuestionRepository;
import io.jsonwebtoken.lang.Assert;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final IQuestionRepository questionRepository;
    private final QuestionMapper questionMapper;
    private final AnswerTypeService answerTypeService;
    private final QuestionValidationService questionValidationService;
    private final ValidationService validationService;
    private final CatalogService catalogService;
    private final AnswerService answerService;

    @Transactional
    public QuestionResponse save(QuestionRequest request) {
        try {
            Assert.notNull(request, "QuestionRequest cannot be null");

            QuestionModel questionModel = questionMapper.toEntity(request);
            QuestionModel savedQuestion = questionRepository.save(questionModel);

            return questionMapper.toDto(savedQuestion);
        } catch (Exception ex) {
            throw new AppException("Failed to save question due to an internal server error", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public QuestionResponse findById(Long id) {
        try {
            Assert.notNull(id, "Id cannot be null");

            QuestionModel questionModel = questionRepository.findById(id)
                    .orElseThrow(() -> new AppException("Question not found with id: " + id, HttpStatus.NOT_FOUND));

            return this.toResponse(questionModel, null);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to find question with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> findAll() {
        try {
            List<QuestionModel> questionModelList = questionRepository.findAll();

            if (questionModelList.isEmpty()) {
                throw new AppException("No questions found", HttpStatus.NOT_FOUND);
            } else {
                return questionModelList.stream()
                        .map(questionMapper::toDto)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            throw new AppException("Failed to fetch questions", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<QuestionModel> questionOptional = questionRepository.findById(id);

            questionOptional.ifPresentOrElse(
                    question -> {
                        question.setStatusKey(Constants.INACTIVE);
                        questionRepository.save(question);
                    },
                    () -> {
                        throw new AppException("Question not found with ID: " + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete question with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private QuestionResponse toResponse(QuestionModel questionModel, @Nullable AnswerResponse answer) {
        QuestionResponse questionResponse = questionMapper.toDto(questionModel);

        AnswerTypeResponse answerType = answerTypeService.findById(questionModel.getAnswerTypeModel().getIdAnswerType());
        questionResponse.setAnswerType(answerType);

        List<QuestionValidationModel> questionValidationList = questionValidationService.findByQuestion(questionModel.getIdQuestion());
        List<ValidationResponse> validationResponseList = validationService.findAllByQuestion(questionValidationList);
        questionResponse.setQuestionValidations(validationResponseList);

        if (answerType.getDescription().equals(Constants.CATALOG)) {
            CatalogResponse catalog = catalogService.findById(questionModel.getCatalogModel().getIdCatalog());
            questionResponse.setCatalog(catalog);
        }

        if (answer != null){
            questionResponse.setAnswer(answer);
        }

        return questionResponse;
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> findAllBySection(String sectionId, String patientId, Long patientMedicalRecordId) {
        List<QuestionModel> questionList = questionRepository.findAllByFormSectionId(sectionId);

        Map<Long, AnswerResponse> answers = answerService.findAllBySectionAndPatientMedicalRecord(questionList, patientId, patientMedicalRecordId);

        return questionList.stream()
                .map(question -> {
                    AnswerResponse answer = answers.get(question.getIdQuestion());
                    return toResponse(question, answer);
                })
                .collect(Collectors.toList());
    }
}
