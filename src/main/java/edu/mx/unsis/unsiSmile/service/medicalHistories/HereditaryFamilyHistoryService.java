package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.HereditaryFamilyHistoryRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.HereditaryFamilyHistoryResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.HereditaryFamilyHistoryMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.HereditaryFamilyHistoryModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IFamilyHistoryRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HereditaryFamilyHistoryService {

    private final IFamilyHistoryRepository familyHistoryRepository;
    private final HereditaryFamilyHistoryMapper familyHistoryMapper;

    @Transactional
    public HereditaryFamilyHistoryResponse createFamilyHistory(@NonNull HereditaryFamilyHistoryRequest familyHistoryRequest) {
        try {
            Assert.notNull(familyHistoryRequest, "FamilyHistoryRequest cannot be null");

            // Map the DTO request to the entity
            HereditaryFamilyHistoryModel familyHistoryModel = familyHistoryMapper.toEntity(familyHistoryRequest);

            // Save the entity to the database
            HereditaryFamilyHistoryModel savedFamilyHistory = familyHistoryRepository.save(familyHistoryModel);

            // Map the saved entity back to a response DTO
            return familyHistoryMapper.toDto(savedFamilyHistory);
        } catch (Exception ex) {
            throw new AppException("Failed to create family history", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public HereditaryFamilyHistoryResponse getFamilyHistoryById(@NonNull Long familyHistoryId) {
        try {
            // Find the family history in the database
            HereditaryFamilyHistoryModel familyHistoryModel = familyHistoryRepository.findById(familyHistoryId)
                    .orElseThrow(() -> new AppException("Family history not found with ID: " + familyHistoryId, HttpStatus.NOT_FOUND));

            // Map the entity to a response DTO
            return familyHistoryMapper.toDto(familyHistoryModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch family history", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<HereditaryFamilyHistoryResponse> getAllFamilyHistories() {
        try {
            List<HereditaryFamilyHistoryModel> allFamilyHistories = familyHistoryRepository.findAll();
            return allFamilyHistories.stream()
                    .map(familyHistoryMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch family histories", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public HereditaryFamilyHistoryResponse updateFamilyHistory(@NonNull Long familyHistoryId, @NonNull HereditaryFamilyHistoryRequest updatedFamilyHistoryRequest) {
        try {
            Assert.notNull(updatedFamilyHistoryRequest, "Updated FamilyHistoryRequest cannot be null");

            // Find the family history in the database
            HereditaryFamilyHistoryModel familyHistoryModel = familyHistoryRepository.findById(familyHistoryId)
                    .orElseThrow(() -> new AppException("Family history not found with ID: " + familyHistoryId, HttpStatus.NOT_FOUND));

            // Update the family history entity with the new data
            familyHistoryMapper.updateEntity(updatedFamilyHistoryRequest, familyHistoryModel);

            // Save the updated entity
            HereditaryFamilyHistoryModel updatedFamilyHistory = familyHistoryRepository.save(familyHistoryModel);

            // Map the updated entity back to a response DTO
            return familyHistoryMapper.toDto(updatedFamilyHistory);
        } catch (Exception ex) {
            throw new AppException("Failed to update family history", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteFamilyHistoryById(@NonNull Long familyHistoryId) {
        try {
            // Check if the family history exists
            if (!familyHistoryRepository.existsById(familyHistoryId)) {
                throw new AppException("Family history not found with ID: " + familyHistoryId, HttpStatus.NOT_FOUND);
            }
            familyHistoryRepository.deleteById(familyHistoryId);
        } catch (Exception ex) {
            throw new AppException("Failed to delete family history", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
