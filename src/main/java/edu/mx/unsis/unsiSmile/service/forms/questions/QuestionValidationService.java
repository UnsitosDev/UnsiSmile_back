package edu.mx.unsis.unsiSmile.service.forms.questions;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.forms.questions.QuestionModel;
import edu.mx.unsis.unsiSmile.model.forms.questions.QuestionValidationModel;
import edu.mx.unsis.unsiSmile.model.forms.questions.ValidationModel;
import edu.mx.unsis.unsiSmile.repository.forms.questions.IQuestionValidationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuestionValidationService {

    private final IQuestionValidationRepository questionValidationRepository;

    @Transactional
    public QuestionValidationModel save(Long idQuestion, Long idValidation) {
        try {
            return questionValidationRepository.save(toEntity(idQuestion, idValidation));
        } catch (Exception ex) {
            throw new AppException("Failed to save question validation", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public QuestionValidationModel findById(Long id) {
        try {
            return questionValidationRepository.findById(id)
                    .orElseThrow(() -> new AppException("Question validation not found with id: " + id, HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new AppException("Failed to find question validation with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<QuestionValidationModel> findAll() {
        try {
            List<QuestionValidationModel> questionValidationModelList = questionValidationRepository.findAll();
            if (questionValidationModelList.isEmpty()) {
                throw new AppException("No question validations found", HttpStatus.NOT_FOUND);
            }
            return questionValidationModelList;
        } catch (Exception ex) {
            throw new AppException("Failed to fetch question validations", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<QuestionValidationModel> questionValidationOptional = questionValidationRepository.findById(id);
            questionValidationOptional.ifPresentOrElse(
                    qv ->{
                        qv.setStatusKey(Constants.INACTIVE);
                        questionValidationRepository.save(qv);
                    },
                    () -> {
                        throw new AppException("Question validation not found with ID: " + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete question validation with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<QuestionValidationModel> findByQuestion(Long questionId) {
        try {
            return questionValidationRepository.findAllByQuestionId(questionId);
        }catch (Exception ex) {
            throw new AppException("Failed to fetch question validations", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private QuestionValidationModel toEntity(Long idQuestion, Long idValidation) {
        return QuestionValidationModel.builder()
                .questionModel(QuestionModel.builder()
                        .idQuestion(idQuestion)
                        .build())
                .validationModel(ValidationModel.builder()
                        .idValidation(idValidation)
                        .build())
                .build();
    }
}