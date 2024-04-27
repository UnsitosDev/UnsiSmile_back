package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothRegionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothRegionResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.ToothRegionMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ToothRegionModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IToothRegionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToothRegionService {

    private final IToothRegionRepository toothRegionRepository;
    private final ToothRegionMapper toothRegionMapper;

    @Transactional
    public ToothRegionResponse createToothRegion(@NonNull ToothRegionRequest toothRegionRequest) {
        try {
            Assert.notNull(toothRegionRequest, "ToothRegionRequest cannot be null");

            ToothRegionModel toothRegionModel = toothRegionMapper.toEntity(toothRegionRequest);
            ToothRegionModel savedToothRegion = toothRegionRepository.save(toothRegionModel);

            return toothRegionMapper.toDto(savedToothRegion);
        } catch (Exception ex) {
            throw new AppException("Failed to create tooth region", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public ToothRegionResponse getToothRegionById(@NonNull Long id) {
        try {
            ToothRegionModel toothRegionModel = toothRegionRepository.findById(id)
                    .orElseThrow(() -> new AppException("Tooth region not found with ID: " + id, HttpStatus.NOT_FOUND));

            return toothRegionMapper.toDto(toothRegionModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch tooth region", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<ToothRegionResponse> getAllToothRegions() {
        try {
            List<ToothRegionModel> allToothRegions = toothRegionRepository.findAll();
            return allToothRegions.stream()
                    .map(toothRegionMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch tooth regions", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ToothRegionResponse updateToothRegion(@NonNull Long id, @NonNull ToothRegionRequest updatedToothRegionRequest) {
        try {
            Assert.notNull(updatedToothRegionRequest, "Updated ToothRegionRequest cannot be null");

            ToothRegionModel toothRegionModel = toothRegionRepository.findById(id)
                    .orElseThrow(() -> new AppException("Tooth region not found with ID: " + id, HttpStatus.NOT_FOUND));

            toothRegionMapper.updateEntity(updatedToothRegionRequest, toothRegionModel);
            ToothRegionModel updatedToothRegion = toothRegionRepository.save(toothRegionModel);

            return toothRegionMapper.toDto(updatedToothRegion);
        } catch (Exception ex) {
            throw new AppException("Failed to update tooth region", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteToothRegionById(@NonNull Long id) {
        try {
            if (!toothRegionRepository.existsById(id)) {
                throw new AppException("Tooth region not found with ID: " + id, HttpStatus.NOT_FOUND);
            }

            toothRegionRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete tooth region", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
