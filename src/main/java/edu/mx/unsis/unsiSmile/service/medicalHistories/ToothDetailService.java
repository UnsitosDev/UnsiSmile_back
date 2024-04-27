package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.ToothDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.ToothDetailResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.ToothDetailMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ToothDetailModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IToothDetailRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToothDetailService {

    private final IToothDetailRepository toothDetailRepository;
    private final ToothDetailMapper toothDetailMapper;

    @Transactional
    public ToothDetailResponse createToothDetail(@NonNull ToothDetailRequest request) {
        try {
            Assert.notNull(request, "ToothDetailRequest cannot be null");

            ToothDetailModel model = toothDetailMapper.toEntity(request);
            ToothDetailModel savedModel = toothDetailRepository.save(model);

            return toothDetailMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to create tooth detail", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public ToothDetailResponse getToothDetailById(@NonNull Long id) {
        try {
            ToothDetailModel model = toothDetailRepository.findById(id)
                    .orElseThrow(() -> new AppException("Tooth detail not found with ID: " + id, HttpStatus.NOT_FOUND));

            return toothDetailMapper.toDto(model);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch tooth detail", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<ToothDetailResponse> getAllToothDetails() {
        try {
            List<ToothDetailModel> allModels = toothDetailRepository.findAll();
            return allModels.stream()
                    .map(toothDetailMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all tooth details", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public ToothDetailResponse updateToothDetail(@NonNull Long id, @NonNull ToothDetailRequest request) {
        try {
            Assert.notNull(request, "Updated ToothDetailRequest cannot be null");

            ToothDetailModel existingModel = toothDetailRepository.findById(id)
                    .orElseThrow(() -> new AppException("Tooth detail not found with ID: " + id, HttpStatus.NOT_FOUND));

            ToothDetailModel updatedModel = toothDetailMapper.toEntity(request);
            updatedModel.setIdToothDetail(existingModel.getIdToothDetail()); // Ensure ID consistency

            ToothDetailModel savedModel = toothDetailRepository.save(updatedModel);

            return toothDetailMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to update tooth detail", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteToothDetail(@NonNull Long id) {
        try {
            if (!toothDetailRepository.existsById(id)) {
                throw new AppException("Tooth detail not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            toothDetailRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete tooth detail", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
