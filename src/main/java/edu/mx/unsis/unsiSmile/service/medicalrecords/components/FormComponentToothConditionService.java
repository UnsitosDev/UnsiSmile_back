package edu.mx.unsis.unsiSmile.service.medicalrecords.components;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalrecords.components.FormComponentToothConditionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.components.FormComponentToothConditionResponse;
import edu.mx.unsis.unsiSmile.dtos.response.medicalrecords.teeth.ToothConditionResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalrecords.components.FormComponentToothConditionMapper;
import edu.mx.unsis.unsiSmile.model.medicalrecords.components.FormComponentModel;
import edu.mx.unsis.unsiSmile.model.medicalrecords.components.FormComponentToothConditionModel;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.components.IFormComponentRepository;
import edu.mx.unsis.unsiSmile.repository.medicalrecords.components.IFormComponentToothConditionRepository;
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
public class FormComponentToothConditionService {

    private final FormComponentToothConditionMapper formComponentToothConditionMapper;
    private final IFormComponentToothConditionRepository formComponentToothConditionRepository;
    private final IFormComponentRepository formComponentRepository;

    @Transactional
    public FormComponentToothConditionResponse create(@NonNull FormComponentToothConditionRequest request) {
        try {
            Assert.notNull(request, ResponseMessages.REQUEST_CANNOT_BE_NULL);

            FormComponentToothConditionModel model = formComponentToothConditionMapper.toEntity(request);
            FormComponentToothConditionModel savedModel = formComponentToothConditionRepository.save(model);

            return formComponentToothConditionMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_FORM_COMPONENT_TOOTH_CONDITION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public FormComponentToothConditionResponse getById(@NonNull Long id) {
        try {
            FormComponentToothConditionModel model = formComponentToothConditionRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.FORM_COMPONENT_TOOTH_CONDITION_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            return formComponentToothConditionMapper.toDto(model);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_FORM_COMPONENT_TOOTH_CONDITION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<FormComponentToothConditionResponse> getAll() {
        try {
            List<FormComponentToothConditionModel> models = formComponentToothConditionRepository.findAll();
            return formComponentToothConditionMapper.toDtos(models);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_FORM_COMPONENTS_TOOTH_CONDITION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public FormComponentToothConditionResponse update(@NonNull Long id, @NonNull FormComponentToothConditionRequest request) {
        try {
            Assert.notNull(request, ResponseMessages.REQUEST_CANNOT_BE_NULL);

            FormComponentToothConditionModel existingModel = formComponentToothConditionRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.FORM_COMPONENT_TOOTH_CONDITION_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            formComponentToothConditionMapper.updateEntity(request, existingModel);
            FormComponentToothConditionModel updatedModel = formComponentToothConditionRepository.save(existingModel);

            return formComponentToothConditionMapper.toDto(updatedModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_FORM_COMPONENT_TOOTH_CONDITION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void delete(@NonNull Long id) {
        try {
            if (!formComponentToothConditionRepository.existsById(id)) {
                throw new AppException(
                        String.format(ResponseMessages.FORM_COMPONENT_TOOTH_CONDITION_NOT_FOUND, id),
                        HttpStatus.NOT_FOUND);
            }
            formComponentToothConditionRepository.deleteById(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_FORM_COMPONENT_TOOTH_CONDITION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<ToothConditionResponse> getToothConditionsByComponent(String formName) {
        try {
            FormComponentModel formComponent = formComponentRepository.findByDescription(formName)
                    .orElseThrow(() -> new AppException(
                            ResponseMessages.FORM_COMPONENT_NOT_FOUND,
                            HttpStatus.NOT_FOUND));

            List<FormComponentToothConditionModel> models = formComponentToothConditionRepository.findByFormComponent(formComponent);

            return models.stream()
                    .map(model -> formComponentToothConditionMapper.toDto(model).getToothCondition())
                    .collect(Collectors.toList());
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_FORM_COMPONENTS_TOOTH_CONDITION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}