
package edu.mx.unsis.unsiSmile.service.people;

import edu.mx.unsis.unsiSmile.dtos.request.people.GenderRequest;
import edu.mx.unsis.unsiSmile.dtos.response.people.GenderResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.people.GenderMapper;
import edu.mx.unsis.unsiSmile.model.people.GenderModel;
import edu.mx.unsis.unsiSmile.repository.people.IGenderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenderService {

    private final IGenderRepository genderRepository;
    private final GenderMapper genderMapper;

    @Transactional
    public GenderResponse createGender(@NonNull GenderRequest genderRequest) {
        try {
            Assert.notNull(genderRequest, "GenderRequest cannot be null");

            // Map the DTO request to the entity
            GenderModel genderModel = genderMapper.toEntity(genderRequest);

            // Save the entity to the database
            GenderModel savedGender = genderRepository.save(genderModel);

            // Map the saved entity back to a response DTO
            return genderMapper.toDto(savedGender);
        } catch (Exception ex) {
            throw new AppException("Failed to create gender", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public GenderResponse getGenderById(@NonNull Long id) {
        try {

            // Find the gender in the database
            GenderModel genderModel = genderRepository.findById(id)
                    .orElseThrow(() -> new AppException("Gender not found with ID: " + id, HttpStatus.NOT_FOUND));

            // Map the entity to a response DTO
            return genderMapper.toDto(genderModel);
        } catch (EmptyResultDataAccessException ex) {
            throw new AppException("Gender not found with ID: " + id, HttpStatus.NOT_FOUND, ex);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch gender", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<GenderResponse> getAllGenders() {
        try {
            List<GenderModel> allGenders = genderRepository.findAll();
            return allGenders.stream()
                    .map(genderMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch genders", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public GenderResponse updateGender(@NonNull Long id, @NonNull GenderRequest updatedGenderRequest) {
        try {
            // Assert.hasText(id, "Gender ID cannot be null or empty");
            Assert.notNull(updatedGenderRequest, "Updated GenderRequest cannot be null");

            // Find the gender in the database
            GenderModel genderModel = genderRepository.findById(id)
                    .orElseThrow(() -> new AppException("Gender not found with ID: " + id, HttpStatus.NOT_FOUND));

            // Update the gender entity with the new data
            genderMapper.updateEntity(updatedGenderRequest, genderModel);

            // Save the updated entity
            GenderModel updatedGender = genderRepository.save(genderModel);

            // Map the updated entity back to a response DTO
            return genderMapper.toDto(updatedGender);
        } catch (Exception ex) {
            throw new AppException("Failed to update gender", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteGenderById(@NonNull Long id) {
        try {
            // Assert.hasText(id, "Gender ID cannot be null or empty");

            // Check if the gender exists
            if (!genderRepository.existsById(id)) {
                throw new AppException("Gender not found with ID: " + id, HttpStatus.NOT_FOUND);
            }

            // Delete the gender
            genderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new AppException("Gender not found with ID: " + id, HttpStatus.NOT_FOUND, ex);
        } catch (Exception ex) {
            throw new AppException("Failed to delete gender", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}