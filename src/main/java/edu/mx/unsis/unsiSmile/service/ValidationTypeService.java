package edu.mx.unsis.unsiSmile.service;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.dtos.request.forms.questions.ValidationTypeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.forms.questions.ValidationTypeResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.forms.questions.ValidationTypeMapper;
import edu.mx.unsis.unsiSmile.model.forms.questions.ValidationTypeModel;
import edu.mx.unsis.unsiSmile.repository.IValidationTypeRepository;
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
public class ValidationTypeService {
    private final IValidationTypeRepository validationTypeRepository;
    private final ValidationTypeMapper validationTypeMapper;

    @Transactional
    public void save(ValidationTypeRequest request) {
        try {
            Assert.notNull(request, "ValidationTypeRequest cannot be null");

            ValidationTypeModel validationTypeModel = validationTypeMapper.toEntity(request);

            validationTypeRepository.save(validationTypeModel);
        } catch (Exception ex) {
            throw new AppException("Failed to save validation type", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public ValidationTypeResponse findById(Long id) {
        try {
            Assert.notNull(id, "Id cannot be null");

            ValidationTypeModel validationTypeModel = validationTypeRepository.findById(id)
                    .orElseThrow(() -> new AppException("Validation type not found with id: " + id, HttpStatus.NOT_FOUND));

            return validationTypeMapper.toDto(validationTypeModel);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to find validation type with id: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<ValidationTypeResponse> findAll() {
        try {
            List<ValidationTypeModel> validationTypeModelList = validationTypeRepository.findAll();

            if (validationTypeModelList.isEmpty()) {
                throw new AppException("No validation types found", HttpStatus.NOT_FOUND);
            } else {
                return validationTypeModelList.stream()
                        .map(validationTypeMapper::toDto)
                        .collect(Collectors.toList());
            }
        } catch (Exception ex) {
            throw new AppException("Failed to fetch validation types", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        try {
            Optional<ValidationTypeModel> validationTypeOptional = validationTypeRepository.findById(id);

            validationTypeOptional.ifPresentOrElse(
                    validationType -> {
                        validationType.setStatusKey(Constants.INACTIVE);
                        validationTypeRepository.save(validationType);
                    },
                    () -> {
                        throw new AppException("Validation type not found with ID: " + id, HttpStatus.NOT_FOUND);
                    }
            );
        } catch (Exception ex) {
            throw new AppException("Failed to delete validation type with ID: " + id, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
