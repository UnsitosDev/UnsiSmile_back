package edu.mx.unsis.unsiSmile.service.patients;

import edu.mx.unsis.unsiSmile.dtos.request.patients.demographics.NationalityRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.demographics.NationalityResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.patients.NationalityMapper;
import edu.mx.unsis.unsiSmile.model.patients.demographics.NationalityModel;
import edu.mx.unsis.unsiSmile.repository.patients.INationalityRepository;
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
public class NationalityService {

    private final INationalityRepository nationalityRepository;
    private final NationalityMapper nationalityMapper;

    @Transactional
    public NationalityResponse createNationality(@NonNull NationalityRequest nationalityRequest) {
        try {
            Assert.notNull(nationalityRequest, "NationalityRequest cannot be null");

            // Map the DTO request to the entity
            NationalityModel nationalityModel = nationalityMapper.toEntity(nationalityRequest);

            // Save the entity to the database
            NationalityModel savedNationality = nationalityRepository.save(nationalityModel);

            // Map the saved entity back to a response DTO
            return nationalityMapper.toDto(savedNationality);
        } catch (Exception ex) {
            throw new AppException("Failed to create nationality", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public NationalityResponse getNationalityById(@NonNull Long idNationality) {
        try {
            NationalityModel nationalityModel = nationalityRepository.findByIdNationality(idNationality)
                    .orElseThrow(() -> new AppException("Nationality not found with ID: " + idNationality, HttpStatus.NOT_FOUND));

            return nationalityMapper.toDto(nationalityModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch nationality by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public NationalityResponse getNationalityByName(@NonNull String nationality) {
        try {
            NationalityModel nationalityModel = nationalityRepository.findByNationality(nationality)
                    .orElseThrow(() -> new AppException("Nationality not found with name: " + nationality, HttpStatus.NOT_FOUND));

            return nationalityMapper.toDto(nationalityModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch nationality by name", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public Page<NationalityResponse> getAllNationalities(Pageable pageable, String keyword) {
        try {
            Page<NationalityModel> allNationalities;
            if (keyword != null && !keyword.isEmpty()) {
                allNationalities = nationalityRepository.findByKeyword(keyword, pageable);
            } else {
                allNationalities = nationalityRepository.findAll(pageable);
            }
            return allNationalities.map(nationalityMapper::toDto);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all nationalities", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public NationalityResponse updateNationality(@NonNull Long idNationality, @NonNull NationalityRequest updatedNationalityRequest) {
        try {
            Assert.notNull(updatedNationalityRequest, "Updated NationalityRequest cannot be null");

            // Find the nationality in the database
            NationalityModel nationalityModel = nationalityRepository.findByIdNationality(idNationality)
                    .orElseThrow(() -> new AppException("Nationality not found with ID: " + idNationality, HttpStatus.NOT_FOUND));

            // Update the nationality entity with the new data
            nationalityMapper.updateEntity(updatedNationalityRequest, nationalityModel);

            // Save the updated entity
            NationalityModel updatedNationality = nationalityRepository.save(nationalityModel);

            // Map the updated entity back to a response DTO
            return nationalityMapper.toDto(updatedNationality);
        } catch (Exception ex) {
            throw new AppException("Failed to update nationality", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteNationalityById(@NonNull Long idNationality) {
        try {
            // Check if the nationality exists
            if (!nationalityRepository.existsById(idNationality)) {
                throw new AppException("Nationality not found with ID: " + idNationality, HttpStatus.NOT_FOUND);
            }
            nationalityRepository.deleteById(idNationality);
        } catch (Exception ex) {
            throw new AppException("Failed to delete nationality", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
