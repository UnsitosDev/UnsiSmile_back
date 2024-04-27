package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothRegionPeriodontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothRegionPeriodontogramResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.ToothRegionPeriodontogramMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ToothRegionPeriodontogramModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IToothRegionPeriodontogramRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToothRegionPeriodontogramService {

    private final IToothRegionPeriodontogramRepository toothRegionPeriodontogramRepository;
    private final ToothRegionPeriodontogramMapper toothRegionPeriodontogramMapper;

    @Transactional
    public ToothRegionPeriodontogramResponse createToothRegionPeriodontogram(@NonNull ToothRegionPeriodontogramRequest request) {
        try {
            Assert.notNull(request, "ToothRegionPeriodontogramRequest cannot be null");

            ToothRegionPeriodontogramModel model = toothRegionPeriodontogramMapper.toEntity(request);
            ToothRegionPeriodontogramModel savedModel = toothRegionPeriodontogramRepository.save(model);

            return toothRegionPeriodontogramMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to create tooth region periodontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public ToothRegionPeriodontogramResponse getToothRegionPeriodontogramById(@NonNull Long id) {
        try {
            ToothRegionPeriodontogramModel model = toothRegionPeriodontogramRepository.findById(id)
                    .orElseThrow(() -> new AppException("Tooth region periodontogram not found with ID: " + id, HttpStatus.NOT_FOUND));

            return toothRegionPeriodontogramMapper.toDto(model);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch tooth region periodontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<ToothRegionPeriodontogramResponse> getAllToothRegionPeriodontograms() {
        try {
            List<ToothRegionPeriodontogramModel> allModels = toothRegionPeriodontogramRepository.findAll();
            return allModels.stream()
                    .map(toothRegionPeriodontogramMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all tooth region periodontograms", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public ToothRegionPeriodontogramResponse updateToothRegionPeriodontogram(@NonNull Long id, @NonNull ToothRegionPeriodontogramRequest request) {
        try {
            Assert.notNull(request, "Updated ToothRegionPeriodontogramRequest cannot be null");

            ToothRegionPeriodontogramModel existingModel = toothRegionPeriodontogramRepository.findById(id)
                    .orElseThrow(() -> new AppException("Tooth region periodontogram not found with ID: " + id, HttpStatus.NOT_FOUND));

            ToothRegionPeriodontogramModel updatedModel = toothRegionPeriodontogramMapper.toEntity(request);
            updatedModel.setIdToothRegionsPeriodontogram(existingModel.getIdToothRegionsPeriodontogram()); // Ensure ID consistency

            ToothRegionPeriodontogramModel savedModel = toothRegionPeriodontogramRepository.save(updatedModel);

            return toothRegionPeriodontogramMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to update tooth region periodontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteToothRegionPeriodontogram(@NonNull Long id) {
        try {
            if (!toothRegionPeriodontogramRepository.existsById(id)) {
                throw new AppException("Tooth region periodontogram not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            toothRegionPeriodontogramRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete tooth region periodontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
