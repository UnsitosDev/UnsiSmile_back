package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.HereditaryFamilyHistoryQuestionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.HereditaryFamilyHistoryQuestionResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.HereditaryFamilyHistoryQuestionMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.HereditaryFamilyHistoryQuestionModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IFamilyHistoryQuestionRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HereditaryFamilyHistoryQuestionService {

    private final IFamilyHistoryQuestionRepository familyHistoryQuestionRepository;
    private final HereditaryFamilyHistoryQuestionMapper familyHistoryQuestionMapper;

    @Transactional
    public HereditaryFamilyHistoryQuestionResponse createFamilyHistoryQuestion(@NonNull HereditaryFamilyHistoryQuestionRequest familyHistoryQuestionRequest) {
        try {
            Assert.notNull(familyHistoryQuestionRequest, "FamilyHistoryQuestionRequest cannot be null");

            // Map the DTO request to the entity
            HereditaryFamilyHistoryQuestionModel familyHistoryQuestionModel = familyHistoryQuestionMapper.toEntity(familyHistoryQuestionRequest);

            // Save the entity to the database
            HereditaryFamilyHistoryQuestionModel savedFamilyHistoryQuestion = familyHistoryQuestionRepository.save(familyHistoryQuestionModel);

            // Map the saved entity back to a response DTO
            return familyHistoryQuestionMapper.toDto(savedFamilyHistoryQuestion);
        } catch (Exception ex) {
            throw new AppException("Failed to create family history question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public HereditaryFamilyHistoryQuestionResponse getFamilyHistoryQuestionById(@NonNull Long familyHistoryQuestionId) {
        try {
            // Find the family history question in the database
            HereditaryFamilyHistoryQuestionModel familyHistoryQuestionModel = familyHistoryQuestionRepository.findById(familyHistoryQuestionId)
                    .orElseThrow(() -> new AppException("Family history question not found with ID: " + familyHistoryQuestionId, HttpStatus.NOT_FOUND));

            // Map the entity to a response DTO
            return familyHistoryQuestionMapper.toDto(familyHistoryQuestionModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch family history question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<HereditaryFamilyHistoryQuestionResponse> getAllFamilyHistoryQuestions() {
        try {
            List<HereditaryFamilyHistoryQuestionModel> allFamilyHistoryQuestions = familyHistoryQuestionRepository.findAll();
            return allFamilyHistoryQuestions.stream()
                    .map(familyHistoryQuestionMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch family history questions", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public HereditaryFamilyHistoryQuestionResponse updateFamilyHistoryQuestion(@NonNull Long familyHistoryQuestionId, @NonNull HereditaryFamilyHistoryQuestionRequest updatedFamilyHistoryQuestionRequest) {
        try {
            Assert.notNull(updatedFamilyHistoryQuestionRequest, "Updated FamilyHistoryQuestionRequest cannot be null");

            // Find the family history question in the database
            HereditaryFamilyHistoryQuestionModel familyHistoryQuestionModel = familyHistoryQuestionRepository.findById(familyHistoryQuestionId)
                    .orElseThrow(() -> new AppException("Family history question not found with ID: " + familyHistoryQuestionId, HttpStatus.NOT_FOUND));

            // Update the family history question entity with the new data
            familyHistoryQuestionMapper.updateEntity(updatedFamilyHistoryQuestionRequest, familyHistoryQuestionModel);

            // Save the updated entity
            HereditaryFamilyHistoryQuestionModel updatedFamilyHistoryQuestion = familyHistoryQuestionRepository.save(familyHistoryQuestionModel);

            // Map the updated entity back to a response DTO
            return familyHistoryQuestionMapper.toDto(updatedFamilyHistoryQuestion);
        } catch (Exception ex) {
            throw new AppException("Failed to update family history question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteFamilyHistoryQuestionById(@NonNull Long familyHistoryQuestionId) {
        try {
            // Check if the family history question exists
            if (!familyHistoryQuestionRepository.existsById(familyHistoryQuestionId)) {
                throw new AppException("Family history question not found with ID: " + familyHistoryQuestionId, HttpStatus.NOT_FOUND);
            }
            familyHistoryQuestionRepository.deleteById(familyHistoryQuestionId);
        } catch (Exception ex) {
            throw new AppException("Failed to delete family history question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
