package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.RegionMeasurementPocketsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.RegionMeasurementPocketsResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.RegionMeasurementPocketsMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.RegionMeasurementPocketsModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IRegionMeasurementPocketsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RegionMeasurementPocketsService {

    private final IRegionMeasurementPocketsRepository regionMeasurementPocketsRepository;
    private final RegionMeasurementPocketsMapper regionMeasurementPocketsMapper;

    @Transactional
    public RegionMeasurementPocketsResponse createRegionMeasurementPockets(@NonNull RegionMeasurementPocketsRequest request) {
        try {
            Assert.notNull(request, "RegionMeasurementPocketsRequest cannot be null");

            RegionMeasurementPocketsModel model = regionMeasurementPocketsMapper.toEntity(request);
            RegionMeasurementPocketsModel savedModel = regionMeasurementPocketsRepository.save(model);

            return regionMeasurementPocketsMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to create region measurement pockets", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public RegionMeasurementPocketsResponse getRegionMeasurementPocketsById(@NonNull Long id) {
        try {
            RegionMeasurementPocketsModel model = regionMeasurementPocketsRepository.findById(id)
                    .orElseThrow(() -> new AppException("Region measurement pockets not found with ID: " + id, HttpStatus.NOT_FOUND));

            return regionMeasurementPocketsMapper.toDto(model);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch region measurement pockets", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<RegionMeasurementPocketsResponse> getAllRegionMeasurementPockets() {
        try {
            List<RegionMeasurementPocketsModel> allModels = regionMeasurementPocketsRepository.findAll();
            return allModels.stream()
                    .map(regionMeasurementPocketsMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all region measurement pockets", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public RegionMeasurementPocketsResponse updateRegionMeasurementPockets(@NonNull Long id, @NonNull RegionMeasurementPocketsRequest request) {
        try {
            Assert.notNull(request, "Updated RegionMeasurementPocketsRequest cannot be null");

            RegionMeasurementPocketsModel existingModel = regionMeasurementPocketsRepository.findById(id)
                    .orElseThrow(() -> new AppException("Region measurement pockets not found with ID: " + id, HttpStatus.NOT_FOUND));

            RegionMeasurementPocketsModel updatedModel = regionMeasurementPocketsMapper.toEntity(request);
            updatedModel.setIdRegionsMeasurementPockets(existingModel.getIdRegionsMeasurementPockets()); // Ensure ID consistency

            RegionMeasurementPocketsModel savedModel = regionMeasurementPocketsRepository.save(updatedModel);

            return regionMeasurementPocketsMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to update region measurement pockets", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteRegionMeasurementPockets(@NonNull Long id) {
        try {
            if (!regionMeasurementPocketsRepository.existsById(id)) {
                throw new AppException("Region measurement pockets not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            regionMeasurementPocketsRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete region measurement pockets", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
