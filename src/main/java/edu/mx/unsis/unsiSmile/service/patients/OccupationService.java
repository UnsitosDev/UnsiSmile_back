package edu.mx.unsis.unsiSmile.service.patients;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.patients.demographics.OccupationRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.demographics.OccupationResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.patients.demographics.OccupationMapper;
import edu.mx.unsis.unsiSmile.model.patients.demographics.OccupationModel;
import edu.mx.unsis.unsiSmile.repository.patients.IOccupationRepository;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

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
    public Page<OccupationResponse> getAllOccupations(Pageable pageable) {
        try {
            Page<OccupationModel> allOccupations = occupationRepository.findAll(pageable);
            return allOccupations.map(occupationMapper::toDto);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all occupations", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public Page<OccupationResponse> getAllOccupations(int page, int size, String order, boolean asc, String keyword) {
        // Validate the order parameter
        List<String> validFields = Arrays.asList("occupation");
        if (order != null && !order.isEmpty() && !validFields.contains(order)) {
            throw new AppException("Invalid order field: " + order, HttpStatus.BAD_REQUEST);
        }

        Sort sort = (order == null || order.isEmpty())
                ? Sort.unsorted()
                : (asc ? Sort.by(order).ascending() : Sort.by(order).descending());
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<OccupationModel> occupationPage;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            occupationPage = occupationRepository.findAllBySearchInput(keyword, pageable);
        } else {
            occupationPage = occupationRepository.findAll(pageable);
        }
        
        return occupationPage.map(occupationMapper::toDto);
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

    @Transactional
    public OccupationModel findOrCreateOccupation(OccupationRequest occupation) {
        try {
            return occupationRepository.findByOccupation(occupation.getOccupation())
                    .orElseGet(() -> occupationRepository.save(occupationMapper.toEntity(occupation)));
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.ERROR_CREATING_OCCUPATION, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
