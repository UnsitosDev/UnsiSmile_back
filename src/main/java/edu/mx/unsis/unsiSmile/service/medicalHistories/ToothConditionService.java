package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothConditionResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.ToothConditionMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothConditionModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.teeth.IToothConditionRepository;
import edu.mx.unsis.unsiSmile.service.medicalHistories.teeth.FormComponentToothConditionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToothConditionService {

    private final IToothConditionRepository toothConditionRepository;
    private final ToothConditionMapper toothConditionMapper;
    private final FormComponentToothConditionService formComponentToothConditionService;

    @Transactional
    public ToothConditionResponse createToothCondition(@NonNull ToothConditionRequest toothConditionRequest) {
        try {
            Assert.notNull(toothConditionRequest, "ToothConditionRequest cannot be null");

            ToothConditionModel toothConditionModel = toothConditionMapper.toEntity(toothConditionRequest);
            ToothConditionModel savedToothCondition = toothConditionRepository.save(toothConditionModel);

            return toothConditionMapper.toDto(savedToothCondition);
        } catch (Exception ex) {
            throw new AppException("Failed to create tooth condition", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public ToothConditionResponse getToothConditionById(@NonNull Long id) {
        try {
            ToothConditionModel toothConditionModel = toothConditionRepository.findById(id)
                    .orElseThrow(() -> new AppException("Tooth condition not found with ID: " + id, HttpStatus.NOT_FOUND));

            return toothConditionMapper.toDto(toothConditionModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch tooth condition", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<ToothConditionResponse> getAllToothConditions(String formName) {
        try {
            return formComponentToothConditionService.getToothConditionsByComponent(formName);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Failed to fetch tooth conditions", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ToothConditionResponse updateToothCondition(@NonNull Long id, @NonNull ToothConditionRequest updatedToothConditionRequest) {
        try {
            Assert.notNull(updatedToothConditionRequest, "Updated ToothConditionRequest cannot be null");

            ToothConditionModel existingToothCondition = toothConditionRepository.findById(id)
                    .orElseThrow(() -> new AppException("Tooth condition not found with ID: " + id, HttpStatus.NOT_FOUND));

            ToothConditionModel updatedToothCondition = toothConditionMapper.toEntity(updatedToothConditionRequest);
            updatedToothCondition.setIdToothCondition(existingToothCondition.getIdToothCondition()); // Ensure ID consistency

            ToothConditionModel savedToothCondition = toothConditionRepository.save(updatedToothCondition);

            return toothConditionMapper.toDto(savedToothCondition);
        } catch (Exception ex) {
            throw new AppException("Failed to update tooth condition", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteToothCondition(@NonNull Long id) {
        try {
            if (!toothConditionRepository.existsById(id)) {
                throw new AppException("Tooth condition not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            toothConditionRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete tooth condition", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public Optional<ToothConditionModel> findByDescription(@NonNull String description) {
        try {
            return toothConditionRepository.findByDescription(description);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch tooth condition by description", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
