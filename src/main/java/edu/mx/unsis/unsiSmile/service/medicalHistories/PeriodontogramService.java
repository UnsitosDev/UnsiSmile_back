package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.PeriodontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.PeriodontogramResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.PeriodontogramMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.PeriodontogramModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IPeriodontogramRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PeriodontogramService {

    private final IPeriodontogramRepository periodontogramRepository;
    private final PeriodontogramMapper periodontogramMapper;

    @Transactional
    public PeriodontogramResponse createPeriodontogram(@NonNull PeriodontogramRequest request) {
        try {
            Assert.notNull(request, "PeriodontogramRequest cannot be null");

            PeriodontogramModel model = periodontogramMapper.toEntity(request);
            model.setDate(LocalDate.now());
            PeriodontogramModel savedModel = periodontogramRepository.save(model);

            return periodontogramMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to create periodontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public PeriodontogramResponse getPeriodontogramById(@NonNull Long id) {
        try {
            PeriodontogramModel model = periodontogramRepository.findById(id)
                    .orElseThrow(
                            () -> new AppException("Periodontogram not found with ID: " + id, HttpStatus.NOT_FOUND));

            return periodontogramMapper.toDto(model);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch periodontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<PeriodontogramResponse> getAllPeriodontograms() {
        try {
            List<PeriodontogramModel> allModels = periodontogramRepository.findAll();
            return allModels.stream()
                    .map(periodontogramMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all periodontograms", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public PeriodontogramResponse updatePeriodontogram(@NonNull Long id, @NonNull PeriodontogramRequest request) {
        try {
            Assert.notNull(request, "Updated PeriodontogramRequest cannot be null");

            PeriodontogramModel existingModel = periodontogramRepository.findById(id)
                    .orElseThrow(
                            () -> new AppException("Periodontogram not found with ID: " + id, HttpStatus.NOT_FOUND));

            PeriodontogramModel updatedModel = periodontogramMapper.toEntity(request);
            updatedModel.setIdPeriodontogram(existingModel.getIdPeriodontogram()); // Ensure ID consistency

            PeriodontogramModel savedModel = periodontogramRepository.save(updatedModel);

            return periodontogramMapper.toDto(savedModel);
        } catch (Exception ex) {
            throw new AppException("Failed to update periodontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deletePeriodontogram(@NonNull Long id) {
        try {
            if (!periodontogramRepository.existsById(id)) {
                throw new AppException("Periodontogram not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            periodontogramRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete periodontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
