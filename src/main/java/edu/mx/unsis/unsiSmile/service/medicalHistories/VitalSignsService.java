package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.VitalSignsRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.VitalSignsResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.VitalSignsMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.VitalSignsModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IVitalSignsRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VitalSignsService {

    private final IVitalSignsRepository vitalSignsRepository;
    private final VitalSignsMapper vitalSignsMapper;

    @Transactional
    public VitalSignsResponse createVitalSigns(@NonNull VitalSignsRequest vitalSignsRequest) {
        try {
            Assert.notNull(vitalSignsRequest, "VitalSignsRequest cannot be null");

            // Map the DTO request to the entity
            VitalSignsModel vitalSignsModel = vitalSignsMapper.toEntity(vitalSignsRequest);

            // Save the entity to the database
            VitalSignsModel savedVitalSigns = vitalSignsRepository.save(vitalSignsModel);

            // Map the saved entity back to a response DTO
            return vitalSignsMapper.toDto(savedVitalSigns);
        } catch (Exception ex) {
            throw new AppException("Failed to create vital signs", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public VitalSignsResponse getVitalSignsById(@NonNull Long vitalSignsId) {
        try {
            // Find the vital signs in the database
            VitalSignsModel vitalSignsModel = vitalSignsRepository.findById(vitalSignsId)
                    .orElseThrow(() -> new AppException("Vital signs not found with ID: " + vitalSignsId, HttpStatus.NOT_FOUND));

            // Map the entity to a response DTO
            return vitalSignsMapper.toDto(vitalSignsModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch vital signs", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<VitalSignsResponse> getAllVitalSigns() {
        try {
            List<VitalSignsModel> allVitalSigns = vitalSignsRepository.findAll();
            return allVitalSigns.stream()
                    .map(vitalSignsMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch vital signs", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public VitalSignsResponse updateVitalSigns(@NonNull Long vitalSignsId, @NonNull VitalSignsRequest updatedVitalSignsRequest) {
        try {
            Assert.notNull(updatedVitalSignsRequest, "Updated VitalSignsRequest cannot be null");

            // Find the vital signs in the database
            VitalSignsModel vitalSignsModel = vitalSignsRepository.findById(vitalSignsId)
                    .orElseThrow(() -> new AppException("Vital signs not found with ID: " + vitalSignsId, HttpStatus.NOT_FOUND));

            // Update the vital signs entity with the new data
            vitalSignsMapper.updateEntity(updatedVitalSignsRequest, vitalSignsModel);

            // Save the updated entity
            VitalSignsModel updatedVitalSigns = vitalSignsRepository.save(vitalSignsModel);

            // Map the updated entity back to a response DTO
            return vitalSignsMapper.toDto(updatedVitalSigns);
        } catch (Exception ex) {
            throw new AppException("Failed to update vital signs", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteVitalSignsById(@NonNull Long vitalSignsId) {
        try {
            // Check if the vital signs exist
            if (!vitalSignsRepository.existsById(vitalSignsId)) {
                throw new AppException("Vital signs not found with ID: " + vitalSignsId, HttpStatus.NOT_FOUND);
            }
            vitalSignsRepository.deleteById(vitalSignsId);
        } catch (Exception ex) {
            throw new AppException("Failed to delete vital signs", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}