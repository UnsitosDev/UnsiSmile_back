package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FacialExamRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.FacialExamResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.FacialExamMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.FacialExamModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IFacialExamRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacialExamService {

    private final IFacialExamRepository facialExamRepository;
    private final FacialExamMapper facialExamMapper;

    @Transactional
    public FacialExamResponse createFacialExam(@NonNull FacialExamRequest facialExamRequest) {
        try {
            Assert.notNull(facialExamRequest, "FacialExamRequest cannot be null");

            // Map the DTO request to the entity
            FacialExamModel facialExamModel = facialExamMapper.toEntity(facialExamRequest);

            // Save the entity to the database
            FacialExamModel savedFacialExam = facialExamRepository.save(facialExamModel);

            // Map the saved entity back to a response DTO
            return facialExamMapper.toDto(savedFacialExam);
        } catch (Exception ex) {
            throw new AppException("Failed to create facial exam", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public FacialExamResponse getFacialExamById(@NonNull Long facialExamId) {
        try {
            // Find the facial exam in the database
            FacialExamModel facialExamModel = facialExamRepository.findById(facialExamId)
                    .orElseThrow(() -> new AppException("Facial exam not found with ID: " + facialExamId, HttpStatus.NOT_FOUND));

            // Map the entity to a response DTO
            return facialExamMapper.toDto(facialExamModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch facial exam", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<FacialExamResponse> getAllFacialExams() {
        try {
            List<FacialExamModel> allFacialExams = facialExamRepository.findAll();
            return allFacialExams.stream()
                    .map(facialExamMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch facial exams", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public FacialExamResponse updateFacialExam(@NonNull Long facialExamId, @NonNull FacialExamRequest updatedFacialExamRequest) {
        try {
            Assert.notNull(updatedFacialExamRequest, "Updated FacialExamRequest cannot be null");

            // Find the facial exam in the database
            FacialExamModel facialExamModel = facialExamRepository.findById(facialExamId)
                    .orElseThrow(() -> new AppException("Facial exam not found with ID: " + facialExamId, HttpStatus.NOT_FOUND));

            // Update the facial exam entity with the new data
            facialExamMapper.updateEntity(updatedFacialExamRequest, facialExamModel);

            // Save the updated entity
            FacialExamModel updatedFacialExam = facialExamRepository.save(facialExamModel);

            // Map the updated entity back to a response DTO
            return facialExamMapper.toDto(updatedFacialExam);
        } catch (Exception ex) {
            throw new AppException("Failed to update facial exam", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteFacialExamById(@NonNull Long facialExamId) {
        try {
            // Check if the facial exam exists
            if (!facialExamRepository.existsById(facialExamId)) {
                throw new AppException("Facial exam not found with ID: " + facialExamId, HttpStatus.NOT_FOUND);
            }
            facialExamRepository.deleteById(facialExamId);
        } catch (Exception ex) {
            throw new AppException("Failed to delete facial exam", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
