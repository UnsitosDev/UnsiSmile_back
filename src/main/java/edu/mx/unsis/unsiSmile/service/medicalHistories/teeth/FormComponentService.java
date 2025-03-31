package edu.mx.unsis.unsiSmile.service.medicalHistories.teeth;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.teeth.FormComponentRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.teeth.FormComponentResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.teeth.FormComponentMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.FormComponentModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.teeth.IFormComponentRepository;
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
public class FormComponentService {
    private final FormComponentMapper formComponentMapper;
    private final IFormComponentRepository formComponentRepository;

    @Transactional
    public FormComponentResponse createFormComponent(@NonNull FormComponentRequest formComponentRequest) {
        try {
            Assert.notNull(formComponentRequest, ResponseMessages.REQUEST_CANNOT_BE_NULL);

            FormComponentModel formComponentModel = formComponentMapper.toEntity(formComponentRequest);
            FormComponentModel savedFormComponent = formComponentRepository.save(formComponentModel);

            return formComponentMapper.toDto(savedFormComponent);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_FORM_COMPONENT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public FormComponentResponse getFormComponentById(@NonNull Long id) {
        try {
            FormComponentModel formComponentModel = formComponentRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.FORM_COMPONENT_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            return formComponentMapper.toDto(formComponentModel);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_FORM_COMPONENT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<FormComponentResponse> getAllFormComponents() {
        try {
            List<FormComponentModel> allFormComponents = formComponentRepository.findAll();
            return allFormComponents.stream()
                    .map(formComponentMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_FORM_COMPONENTS, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public FormComponentResponse updateFormComponent(@NonNull Long id, @NonNull FormComponentRequest updatedFormComponentRequest) {
        try {
            Assert.notNull(updatedFormComponentRequest, ResponseMessages.REQUEST_CANNOT_BE_NULL);

            FormComponentModel existingFormComponent = formComponentRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.FORM_COMPONENT_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            formComponentMapper.updateEntity(updatedFormComponentRequest, existingFormComponent);
            FormComponentModel updatedFormComponent = formComponentRepository.save(existingFormComponent);

            return formComponentMapper.toDto(updatedFormComponent);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_FORM_COMPONENT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteFormComponent(@NonNull Long id) {
        try {
            if (!formComponentRepository.existsById(id)) {
                throw new AppException(
                        String.format(ResponseMessages.FORM_COMPONENT_NOT_FOUND, id),
                        HttpStatus.NOT_FOUND);
            }
            formComponentRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_FORM_COMPONENT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
