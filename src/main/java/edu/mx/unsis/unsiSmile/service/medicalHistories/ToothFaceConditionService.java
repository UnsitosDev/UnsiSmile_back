package edu.mx.unsis.unsiSmile.service.medicalHistories;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothFaceConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothFaceConditionResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.ToothFaceConditionMapper;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.teeth.FormComponentToothFaceConditionMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothFaceConditionModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.teeth.IToothFaceConditionRepository;
import edu.mx.unsis.unsiSmile.service.medicalHistories.teeth.FormComponentToothFaceConditionService;
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
public class ToothFaceConditionService {

    private final IToothFaceConditionRepository toothFaceConditionRepository;
    private final ToothFaceConditionMapper toothFaceConditionMapper;
    private final FormComponentToothFaceConditionMapper formComponentToothFaceConditionMapper;
    private final FormComponentToothFaceConditionService formComponentToothFaceConditionService;

    @Transactional
    public ToothFaceConditionResponse createToothCondition(@NonNull ToothFaceConditionRequest toothFaceConditionRequest) {
        try {
            Assert.notNull(toothFaceConditionRequest, "ToothFaceConditionRequest cannot be null");

            ToothFaceConditionModel toothConditionModel = toothFaceConditionMapper.toEntity(toothFaceConditionRequest);
            ToothFaceConditionModel savedToothCondition = toothFaceConditionRepository.save(toothConditionModel);

            return toothFaceConditionMapper.toDto(savedToothCondition);
        } catch (Exception ex) {
            throw new AppException("Failed to create tooth condition", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public ToothFaceConditionResponse getToothConditionById(@NonNull Long id) {
        try {
            ToothFaceConditionModel toothConditionModel = toothFaceConditionRepository.findById(id)
                    .orElseThrow(() -> new AppException("Tooth condition not found with ID: " + id, HttpStatus.NOT_FOUND));

            return toothFaceConditionMapper.toDto(toothConditionModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch tooth condition", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<ToothFaceConditionResponse> getAllToothConditions(@NonNull String formName) {
        try {
            return formComponentToothFaceConditionService.getToothFacesByComponent(formName);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException("Failed to fetch tooth conditions", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ToothFaceConditionResponse updateToothCondition(@NonNull Long id, @NonNull ToothFaceConditionRequest updatedToothFaceConditionRequest) {
        try {
            Assert.notNull(updatedToothFaceConditionRequest, "Updated ToothFaceConditionRequest cannot be null");

            ToothFaceConditionModel existingToothCondition = toothFaceConditionRepository.findById(id)
                    .orElseThrow(() -> new AppException("Tooth condition not found with ID: " + id, HttpStatus.NOT_FOUND));

            ToothFaceConditionModel updatedToothCondition = toothFaceConditionMapper.toEntity(updatedToothFaceConditionRequest);
            updatedToothCondition.setIdToothFaceCondition(existingToothCondition.getIdToothFaceCondition()); // Ensure ID consistency

            ToothFaceConditionModel savedToothCondition = toothFaceConditionRepository.save(updatedToothCondition);

            return toothFaceConditionMapper.toDto(savedToothCondition);
        } catch (Exception ex) {
            throw new AppException("Failed to update tooth condition", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteToothCondition(@NonNull Long id) {
        try {
            if (!toothFaceConditionRepository.existsById(id)) {
                throw new AppException("Tooth condition not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            toothFaceConditionRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete tooth condition", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public Optional<ToothFaceConditionModel> findByDescription(@NonNull String description) {
        try {
            return toothFaceConditionRepository.findByDescription(description);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch tooth condition by description", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
