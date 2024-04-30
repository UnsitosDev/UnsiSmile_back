package edu.mx.unsis.unsiSmile.service.patients;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.patients.GuardianRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.GuardianResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.patients.GuardianMapper;
import edu.mx.unsis.unsiSmile.model.patients.GuardianModel;
import edu.mx.unsis.unsiSmile.repository.patients.IGuardianRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuardianService {

    private final IGuardianRepository guardianRepository;
    private final GuardianMapper guardianMapper;

    @Transactional
    public GuardianResponse createGuardian(@NonNull GuardianRequest guardianRequest) {
        try {
            Assert.notNull(guardianRequest, "GuardianRequest cannot be null");

            // Map the DTO request to the entity
            GuardianModel guardianModel = guardianMapper.toEntity(guardianRequest);

            // Save the entity to the database
            GuardianModel savedGuardian = guardianRepository.save(guardianModel);

            // Map the saved entity back to a response DTO
            return guardianMapper.toDto(savedGuardian);
        } catch (Exception ex) {
            throw new AppException("Failed to create guardian", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public GuardianResponse getGuardianById(@NonNull Long idGuardian) {
        try {
            GuardianModel guardianModel = guardianRepository.findByIdGuardian(idGuardian)
                    .orElseThrow(() -> new AppException("Guardian not found with ID: " + idGuardian, HttpStatus.NOT_FOUND));

            return guardianMapper.toDto(guardianModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch guardian by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public GuardianResponse getGuardianByPhone(@NonNull String phone) {
        try {
            GuardianModel guardianModel = guardianRepository.findByPhone(phone)
                    .orElseThrow(() -> new AppException("Guardian not found with phone: " + phone, HttpStatus.NOT_FOUND));

            return guardianMapper.toDto(guardianModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch guardian by phone", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public GuardianResponse getGuardianByEmail(@NonNull String email) {
        try {
            GuardianModel guardianModel = guardianRepository.findByEmail(email)
                    .orElseThrow(() -> new AppException("Guardian not found with email: " + email, HttpStatus.NOT_FOUND));

            return guardianMapper.toDto(guardianModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch guardian by email", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<GuardianResponse> getAllGuardians() {
        try {
            List<GuardianModel> allGuardians = guardianRepository.findAll();
            return allGuardians.stream()
                    .map(guardianMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all guardians", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public GuardianResponse updateGuardian(@NonNull Long idGuardian, @NonNull GuardianRequest updatedGuardianRequest) {
        try {
            Assert.notNull(updatedGuardianRequest, "Updated GuardianRequest cannot be null");

            // Find the guardian in the database
            GuardianModel guardianModel = guardianRepository.findByIdGuardian(idGuardian)
                    .orElseThrow(() -> new AppException("Guardian not found with ID: " + idGuardian, HttpStatus.NOT_FOUND));

            // Update the guardian entity with the new data
            guardianMapper.updateEntity(updatedGuardianRequest, guardianModel);

            // Save the updated entity
            GuardianModel updatedGuardian = guardianRepository.save(guardianModel);

            // Map the updated entity back to a response DTO
            return guardianMapper.toDto(updatedGuardian);
        } catch (Exception ex) {
            throw new AppException("Failed to update guardian", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteGuardianById(@NonNull Long idGuardian) {
        try {
            // Check if the guardian exists
            if (!guardianRepository.existsById(idGuardian)) {
                throw new AppException("Guardian not found with ID: " + idGuardian, HttpStatus.NOT_FOUND);
            }
            guardianRepository.deleteById(idGuardian);
        } catch (Exception ex) {
            throw new AppException("Failed to delete guardian", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
