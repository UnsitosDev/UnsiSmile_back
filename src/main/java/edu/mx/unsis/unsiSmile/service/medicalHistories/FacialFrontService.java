package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FacialFrontRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.FacialFrontResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.FacialFrontMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.FacialFrontModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IFacialFrontRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacialFrontService {

    private final IFacialFrontRepository facialFrontRepository;
    private final FacialFrontMapper facialFrontMapper;

    @Transactional
    public FacialFrontResponse createFacialFront(@NonNull FacialFrontRequest facialFrontRequest) {
        try {
            Assert.notNull(facialFrontRequest, "FacialFrontRequest cannot be null");

            // Map the DTO request to the entity
            FacialFrontModel facialFrontModel = facialFrontMapper.toEntity(facialFrontRequest);

            // Save the entity to the database
            FacialFrontModel savedFacialFront = facialFrontRepository.save(facialFrontModel);

            // Map the saved entity back to a response DTO
            return facialFrontMapper.toDto(savedFacialFront);
        } catch (DataAccessException ex) {
            throw new AppException("Failed to create facial front", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public FacialFrontResponse getFacialFrontById(@NonNull Long facialFrontId) {
        try {
            // Find the facial front in the database
            FacialFrontModel facialFrontModel = facialFrontRepository.findById(facialFrontId)
                    .orElseThrow(() -> new AppException("Facial front not found with ID: " + facialFrontId,
                            HttpStatus.NOT_FOUND));

            // Map the entity to a response DTO
            return facialFrontMapper.toDto(facialFrontModel);
        } catch (EmptyResultDataAccessException ex) {
            throw new AppException("Facial front not found with ID: " + facialFrontId, HttpStatus.NOT_FOUND, ex);
        } catch (DataAccessException ex) {
            throw new AppException("Failed to fetch facial front", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<FacialFrontResponse> getAllFacialFronts() {
        try {
            List<FacialFrontModel> allFacialFronts = facialFrontRepository.findAll();
            return allFacialFronts.stream()
                    .map(facialFrontMapper::toDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new AppException("Failed to fetch facial front", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public FacialFrontResponse updateFacialFront(@NonNull Long facialFrontId,
            @NonNull FacialFrontRequest updatedFacialFrontRequest) {
        try {
            Assert.notNull(updatedFacialFrontRequest, "Updated FacialFrontRequest cannot be null");

            // Find the facial front in the database
            FacialFrontModel facialFrontModel = facialFrontRepository.findById(facialFrontId)
                    .orElseThrow(() -> new AppException("Facial front not found with ID: " + facialFrontId,
                            HttpStatus.NOT_FOUND));

            // Update the facial front entity with the new data
            facialFrontMapper.updateEntity(updatedFacialFrontRequest, facialFrontModel);

            // Save the updated entity
            FacialFrontModel updatedFacialFront = facialFrontRepository.save(facialFrontModel);

            // Map the updated entity back to a response DTO
            return facialFrontMapper.toDto(updatedFacialFront);
        } catch (DataAccessException ex) {
            throw new AppException("Failed to update facial front", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteFacialFrontById(@NonNull Long facialFrontId) {
        try {
            // Check if the facial front exists
            if (!facialFrontRepository.existsById(facialFrontId)) {
                throw new AppException("Facial front not found with ID: " + facialFrontId, HttpStatus.NOT_FOUND);
            }
            facialFrontRepository.deleteById(facialFrontId);
        } catch (EmptyResultDataAccessException ex) {
            throw new AppException("Facial front not found with ID: " + facialFrontId, HttpStatus.NOT_FOUND, ex);
        } catch (DataAccessException ex) {
            throw new AppException("Failed to delete facial front", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public FacialFrontResponse findByFacialFront(@NonNull String facialFront) {
        try {
            // Find the facial front in the database
            Optional<FacialFrontModel> facialFrontModel = facialFrontRepository.findByFacialFront(facialFront);

            // Map the entity to a response DTO
            return facialFrontModel.map(facialFrontMapper::toDto)
                    .orElseThrow(() -> new AppException("Facial front not found with name: " + facialFront,
                            HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            throw new AppException("Failed to fetch facial front by name", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
