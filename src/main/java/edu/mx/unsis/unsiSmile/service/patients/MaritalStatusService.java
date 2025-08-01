package edu.mx.unsis.unsiSmile.service.patients;

import edu.mx.unsis.unsiSmile.dtos.request.patients.demographics.MaritalStatusRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.demographics.MaritalStatusResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.patients.demographics.MaritalStatusMapper;
import edu.mx.unsis.unsiSmile.model.patients.demographics.MaritalStatusModel;
import edu.mx.unsis.unsiSmile.repository.patients.IMaritalStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class MaritalStatusService {

    private final IMaritalStatusRepository maritalStatusRepository;
    private final MaritalStatusMapper maritalStatusMapper;

    @Transactional
    public MaritalStatusResponse createMaritalStatus(@NonNull MaritalStatusRequest maritalStatusRequest) {
        try {
            Assert.notNull(maritalStatusRequest, "MaritalStatusRequest cannot be null");

            // Map the DTO request to the entity
            MaritalStatusModel maritalStatusModel = maritalStatusMapper.toEntity(maritalStatusRequest);

            // Save the entity to the database
            MaritalStatusModel savedMaritalStatus = maritalStatusRepository.save(maritalStatusModel);

            // Map the saved entity back to a response DTO
            return maritalStatusMapper.toDto(savedMaritalStatus);
        } catch (Exception ex) {
            throw new AppException("Failed to create marital status", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public MaritalStatusResponse getMaritalStatusById(@NonNull Long idMaritalStatus) {
        try {
            MaritalStatusModel maritalStatusModel = maritalStatusRepository.findByIdMaritalStatus(idMaritalStatus)
                    .orElseThrow(() -> new AppException("Marital status not found with ID: " + idMaritalStatus, HttpStatus.NOT_FOUND));

            return maritalStatusMapper.toDto(maritalStatusModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch marital status by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public MaritalStatusResponse getMaritalStatusByStatus(@NonNull String maritalStatus) {
        try {
            MaritalStatusModel maritalStatusModel = maritalStatusRepository.findByMaritalStatus(maritalStatus)
                    .orElseThrow(() -> new AppException("Marital status not found with status: " + maritalStatus, HttpStatus.NOT_FOUND));

            return maritalStatusMapper.toDto(maritalStatusModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch marital status by status", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public Page<MaritalStatusResponse> getAllMaritalStatus(Pageable pageable) {
        try {
            Page<MaritalStatusModel> allMaritalStatus = maritalStatusRepository.findAll(pageable);
            return allMaritalStatus.map(maritalStatusMapper::toDto);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all marital status", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public MaritalStatusResponse updateMaritalStatus(@NonNull Long idMaritalStatus, @NonNull MaritalStatusRequest updatedMaritalStatusRequest) {
        try {
            Assert.notNull(updatedMaritalStatusRequest, "Updated MaritalStatusRequest cannot be null");

            // Find the marital status in the database
            MaritalStatusModel maritalStatusModel = maritalStatusRepository.findByIdMaritalStatus(idMaritalStatus)
                    .orElseThrow(() -> new AppException("Marital status not found with ID: " + idMaritalStatus, HttpStatus.NOT_FOUND));

            // Update the marital status entity with the new data
            maritalStatusMapper.updateEntity(updatedMaritalStatusRequest, maritalStatusModel);

            // Save the updated entity
            MaritalStatusModel updatedMaritalStatus = maritalStatusRepository.save(maritalStatusModel);

            // Map the updated entity back to a response DTO
            return maritalStatusMapper.toDto(updatedMaritalStatus);
        } catch (Exception ex) {
            throw new AppException("Failed to update marital status", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteMaritalStatusById(@NonNull Long idMaritalStatus) {
        try {
            // Check if the marital status exists
            if (!maritalStatusRepository.existsById(idMaritalStatus)) {
                throw new AppException("Marital status not found with ID: " + idMaritalStatus, HttpStatus.NOT_FOUND);
            }
            maritalStatusRepository.deleteById(idMaritalStatus);
        } catch (Exception ex) {
            throw new AppException("Failed to delete marital status", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
