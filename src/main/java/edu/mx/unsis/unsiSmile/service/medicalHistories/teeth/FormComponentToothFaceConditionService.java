package edu.mx.unsis.unsiSmile.service.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.teeth.FormComponentToothFaceConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothFaceConditionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.teeth.FormComponentToothFaceConditionResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.teeth.FormComponentToothFaceConditionMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.FormComponentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.FormComponentToothfaceConditionModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.teeth.IFormComponentRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.teeth.IFormComponentToothfaceConditionRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormComponentToothFaceConditionService {
    private final FormComponentToothFaceConditionMapper formComponentToothFaceConditionMapper;
    private final IFormComponentToothfaceConditionRepository formComponentToothFaceConditionRepository;
    private final IFormComponentRepository formComponentRepository;

    @Transactional
    public FormComponentToothFaceConditionResponse create(@NonNull FormComponentToothFaceConditionRequest request) {
        try {
            Assert.notNull(request, ResponseMessages.REQUEST_CANNOT_BE_NULL);

            FormComponentToothfaceConditionModel model = formComponentToothFaceConditionMapper.toEntity(request);
            FormComponentToothfaceConditionModel savedModel = formComponentToothFaceConditionRepository.save(model);

            return formComponentToothFaceConditionMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_FORM_COMPONENT_TOOTH_FACE_CONDITION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public FormComponentToothFaceConditionResponse getById(@NonNull Long id) {
        try {
            FormComponentToothfaceConditionModel model = formComponentToothFaceConditionRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.FORM_COMPONENT_TOOTH_FACE_CONDITION_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            return formComponentToothFaceConditionMapper.toDto(model);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_FORM_COMPONENT_TOOTH_FACE_CONDITION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<FormComponentToothFaceConditionResponse> getAll() {
        try {
            List<FormComponentToothfaceConditionModel> models = formComponentToothFaceConditionRepository.findAll();
            return formComponentToothFaceConditionMapper.toDtos(models);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_FORM_COMPONENTS_TOOTH_FACE_CONDITION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public FormComponentToothFaceConditionResponse update(@NonNull Long id, @NonNull FormComponentToothFaceConditionRequest request) {
        try {
            Assert.notNull(request, ResponseMessages.REQUEST_CANNOT_BE_NULL);

            FormComponentToothfaceConditionModel existingModel = formComponentToothFaceConditionRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.FORM_COMPONENT_TOOTH_FACE_CONDITION_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            formComponentToothFaceConditionMapper.updateEntity(request, existingModel);
            FormComponentToothfaceConditionModel updatedModel = formComponentToothFaceConditionRepository.save(existingModel);

            return formComponentToothFaceConditionMapper.toDto(updatedModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_FORM_COMPONENT_TOOTH_FACE_CONDITION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void delete(@NonNull Long id) {
        try {
            if (!formComponentToothFaceConditionRepository.existsById(id)) {
                throw new AppException(
                        String.format(ResponseMessages.FORM_COMPONENT_TOOTH_FACE_CONDITION_NOT_FOUND, id),
                        HttpStatus.NOT_FOUND);
            }
            formComponentToothFaceConditionRepository.deleteById(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_FORM_COMPONENT_TOOTH_FACE_CONDITION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<ToothFaceConditionResponse> getToothFacesByComponent(@NonNull String formName) {
        try {
            FormComponentModel formComponent = formComponentRepository.findByDescription(formName)
                    .orElseThrow(()-> new AppException(
                            ResponseMessages.FORM_COMPONENT_NOT_FOUND,
                            HttpStatus.NOT_FOUND));

            List<FormComponentToothfaceConditionModel> models = formComponentToothFaceConditionRepository.findByFormComponent(formComponent);

            return models.stream()
                    .map(model -> formComponentToothFaceConditionMapper.toDto(model).getToothFaceCondition())
                    .collect(Collectors.toList());
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_FORM_COMPONENTS_TOOTH_FACE_CONDITION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

