package edu.mx.unsis.unsiSmile.service;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.ValidationRequest;
import edu.mx.unsis.unsiSmile.dtos.response.ValidationResponse;
import edu.mx.unsis.unsiSmile.dtos.response.ValidationTypeResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.ValidationMapper;
import edu.mx.unsis.unsiSmile.model.QuestionValidationModel;
import edu.mx.unsis.unsiSmile.model.ValidationModel;
import edu.mx.unsis.unsiSmile.repository.IValidationRepository;
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
public class ValidationService {
    private final IValidationRepository validationRepository;
    private final ValidationMapper validationMapper;
    private final ValidationTypeService validationTypeService;

    @Transactional
    public void save(ValidationRequest request) {
        try {
            Assert.notNull(request, "ValidationRequest cannot be null");

            ValidationModel validationModel = validationMapper.toEntity(request);

            validationRepository.save(validationModel);
        } catch (Exception ex) {
            throw new AppException("Failed to save validation", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public ValidationResponse findById(Long id) {
        try {
            Assert.notNull(id, "Id cannot be null");

            ValidationModel validationModel = validationRepository.findById(id)
                    .orElseThrow(() -> new AppException("Validation not found with id: " + id, HttpStatus.NOT_FOUND));

            return this.toResponse(validationModel);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to find validation with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<ValidationResponse> findAll() {
        try {
            List<ValidationModel> validationModelList = validationRepository.findAll();

            if (validationModelList.isEmpty()) {
                throw new AppException("No validations found", HttpStatus.NOT_FOUND);
            } else {
                return validationModelList.stream()
                        .map(this::toResponse)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            throw new AppException("Failed to fetch validations", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<ValidationModel> validationOptional = validationRepository.findById(id);

            validationOptional.ifPresentOrElse(
                    validation -> {
                        validation.setStatusKey(Constants.INACTIVE);
                        validationRepository.save(validation);
                    },
                    () -> {
                        throw new AppException("Validation not found with ID: " + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete validation with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<ValidationResponse> findAllByQuestion(List<QuestionValidationModel> questionValidationModels) {
        try {
            Set<Long> validationIds = questionValidationModels.stream()
                    .map(qvm -> qvm.getValidationModel().getIdValidation())
                    .collect(Collectors.toSet());

            List<ValidationModel> validationModels = validationRepository.findAllById(validationIds);

            return validationModels.stream()
                    .map(this::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new RuntimeException("Failed to fetch validation types", ex);
        }
    }

    private ValidationResponse toResponse(ValidationModel validationModel) {
        ValidationTypeResponse typeResponse = validationTypeService.findById(validationModel.getValidationTypeModel().getIdValidationType());
        ValidationResponse validationResponse = validationMapper.toDto(validationModel);
        validationResponse.setValidationType(typeResponse);
        return validationResponse;
    }
}
