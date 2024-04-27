package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.PocketMeasurementDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PocketMeasurementDetailResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.PocketMeasurementDetailMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.PocketMeasurementDetailModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IPocketMeasurementDetailRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PocketMeasurementDetailService {

    private final IPocketMeasurementDetailRepository pocketMeasurementDetailRepository;
    private final PocketMeasurementDetailMapper pocketMeasurementDetailMapper;

    @Transactional
    public PocketMeasurementDetailResponse createPocketMeasurementDetail(@NonNull PocketMeasurementDetailRequest request) {
        try {
            Assert.notNull(request, "PocketMeasurementDetailRequest cannot be null");

            PocketMeasurementDetailModel model = pocketMeasurementDetailMapper.toEntity(request);
            PocketMeasurementDetailModel savedModel = pocketMeasurementDetailRepository.save(model);

            return pocketMeasurementDetailMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to create pocket measurement detail", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public PocketMeasurementDetailResponse getPocketMeasurementDetailById(@NonNull Long id) {
        try {
            PocketMeasurementDetailModel model = pocketMeasurementDetailRepository.findById(id)
                    .orElseThrow(() -> new AppException("Pocket measurement detail not found with ID: " + id, HttpStatus.NOT_FOUND));

            return pocketMeasurementDetailMapper.toDto(model);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch pocket measurement detail", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<PocketMeasurementDetailResponse> getAllPocketMeasurementDetails() {
        try {
            List<PocketMeasurementDetailModel> allModels = pocketMeasurementDetailRepository.findAll();
            return allModels.stream()
                    .map(pocketMeasurementDetailMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all pocket measurement details", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public PocketMeasurementDetailResponse updatePocketMeasurementDetail(@NonNull Long id, @NonNull PocketMeasurementDetailRequest request) {
        try {
            Assert.notNull(request, "Updated PocketMeasurementDetailRequest cannot be null");

            PocketMeasurementDetailModel existingModel = pocketMeasurementDetailRepository.findById(id)
                    .orElseThrow(() -> new AppException("Pocket measurement detail not found with ID: " + id, HttpStatus.NOT_FOUND));

            PocketMeasurementDetailModel updatedModel = pocketMeasurementDetailMapper.toEntity(request);
            updatedModel.setIdPocketMeasurementDetail(existingModel.getIdPocketMeasurementDetail()); // Ensure ID consistency

            PocketMeasurementDetailModel savedModel = pocketMeasurementDetailRepository.save(updatedModel);

            return pocketMeasurementDetailMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to update pocket measurement detail", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deletePocketMeasurementDetail(@NonNull Long id) {
        try {
            if (!pocketMeasurementDetailRepository.existsById(id)) {
                throw new AppException("Pocket measurement detail not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            pocketMeasurementDetailRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete pocket measurement detail", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
