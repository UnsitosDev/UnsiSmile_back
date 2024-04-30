package edu.mx.unsis.unsiSmile.service.patients;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.patients.OccupationRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.OccupationResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.patients.OccupationMapper;
import edu.mx.unsis.unsiSmile.model.patients.OccupationModel;
import edu.mx.unsis.unsiSmile.repository.patients.IOccupationRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OccupationService {

    private final IOccupationRepository occupationRepository;
    private final OccupationMapper occupationMapper;

    @Transactional
    public OccupationResponse createOccupation(@NonNull OccupationRequest occupationRequest) {
        try {
            Assert.notNull(occupationRequest, "OccupationRequest cannot be null");

            // Map the DTO request to the entity
            OccupationModel occupationModel = occupationMapper.toEntity(occupationRequest);

            // Save the entity to the database
            OccupationModel savedOccupation = occupationRepository.save(occupationModel);

            // Map the saved entity back to a response DTO
            return occupationMapper.toDto(savedOccupation);
        } catch (Exception ex) {
            throw new AppException("Failed to create occupation", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public OccupationResponse getOccupationById(@NonNull Long idOccupation) {
        try {
            OccupationModel occupationModel = occupationRepository.findByIdOccupation(idOccupation)
                    .orElseThrow(() -> new AppException("Occupation not found with ID: " + idOccupation, HttpStatus.NOT_FOUND));

            return occupationMapper.toDto(occupationModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch occupation by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public OccupationResponse getOccupationByName(@NonNull String occupation) {
        try {
            OccupationModel occupationModel = occupationRepository.findByOccupation(occupation)
                    .orElseThrow(() -> new AppException("Occupation not found with name: " + occupation, HttpStatus.NOT_FOUND));

            return occupationMapper.toDto(occupationModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch occupation by name", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<OccupationResponse> getAllOccupations() {
        try {
            List<OccupationModel> allOccupations = occupationRepository.findAll();
            return allOccupations.stream()
                    .map(occupationMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all occupations", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public OccupationResponse updateOccupation(@NonNull Long idOccupation, @NonNull OccupationRequest updatedOccupationRequest) {
        try {
            Assert.notNull(updatedOccupationRequest, "Updated OccupationRequest cannot be null");

            // Find the occupation in the database
            OccupationModel occupationModel = occupationRepository.findByIdOccupation(idOccupation)
                    .orElseThrow(() -> new AppException("Occupation not found with ID: " + idOccupation, HttpStatus.NOT_FOUND));

            // Update the occupation entity with the new data
            occupationMapper.updateEntity(updatedOccupationRequest, occupationModel);

            // Save the updated entity
            OccupationModel updatedOccupation = occupationRepository.save(occupationModel);

            // Map the updated entity back to a response DTO
            return occupationMapper.toDto(updatedOccupation);
        } catch (Exception ex) {
            throw new AppException("Failed to update occupation", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteOccupationById(@NonNull Long idOccupation) {
        try {
            // Check if the occupation exists
            if (!occupationRepository.existsById(idOccupation)) {
                throw new AppException("Occupation not found with ID: " + idOccupation, HttpStatus.NOT_FOUND);
            }
            occupationRepository.deleteById(idOccupation);
        } catch (Exception ex) {
            throw new AppException("Failed to delete occupation", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
