package edu.mx.unsis.unsiSmile.service.addresses;

import java.util.List;
import java.util.stream.Collectors;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.HousingRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.HousingResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.addresses.HousingMapper;
import edu.mx.unsis.unsiSmile.model.HousingModel;
import edu.mx.unsis.unsiSmile.repository.addresses.IHousingRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HousingService {

    private final IHousingRepository housingRepository;
    private final HousingMapper housingMapper;

    @Transactional
    public HousingResponse createHousing(@NonNull HousingRequest housingRequest) {
        try {
            Assert.notNull(housingRequest, "HousingRequest cannot be null");

            // Map the DTO request to the entity
            HousingModel housingModel = housingMapper.toEntity(housingRequest);

            // Save the entity to the database
            HousingModel savedHousing = housingRepository.save(housingModel);

            // Map the saved entity back to a response DTO
            return housingMapper.toDto(savedHousing);
        } catch (Exception ex) {
            throw new AppException("Failed to create housing", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public HousingResponse getHousingById(@NonNull String idHousing) {
        try {
            HousingModel housingModel = housingRepository.findByIdHousing(idHousing)
                    .orElseThrow(() -> new AppException("Housing not found with ID: " + idHousing, HttpStatus.NOT_FOUND));

            return housingMapper.toDto(housingModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch housing by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public HousingResponse getHousingByCategory(@NonNull String category) {
        try {
            HousingModel housingModel = housingRepository.findByCategory(category)
                    .orElseThrow(() -> new AppException("Housing not found with category: " + category, HttpStatus.NOT_FOUND));

            return housingMapper.toDto(housingModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch housing by category", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public HousingResponse updateHousing(@NonNull String idHousing, @NonNull HousingRequest updatedHousingRequest) {
        try {
            Assert.notNull(updatedHousingRequest, "Updated HousingRequest cannot be null");

            // Find the housing in the database
            HousingModel housingModel = housingRepository.findByIdHousing(idHousing)
                    .orElseThrow(() -> new AppException("Housing not found with ID: " + idHousing, HttpStatus.NOT_FOUND));

            // Update the housing entity with the new data
            housingMapper.updateEntity(updatedHousingRequest, housingModel);

            // Save the updated entity
            HousingModel updatedHousing = housingRepository.save(housingModel);

            // Map the updated entity back to a response DTO
            return housingMapper.toDto(updatedHousing);
        } catch (Exception ex) {
            throw new AppException("Failed to update housing", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteHousingById(@NonNull String idHousing) {
        try {
            // Check if the housing exists
            if (!housingRepository.existsById(idHousing)) {
                throw new AppException("Housing not found with ID: " + idHousing, HttpStatus.NOT_FOUND);
            }
            housingRepository.deleteById(idHousing);
        } catch (Exception ex) {
            throw new AppException("Failed to delete housing", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<HousingResponse> getHousingsBySearch(String search) {
        List<HousingModel> housings;

        if (search != null && !search.isEmpty()) {
            housings = housingRepository.findByCategoryContaining(search);
        } else {
            housings = housingRepository.findAll();
        }

        return housings.stream()
                .map(housingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public HousingModel findHousingById(@NonNull String id) {
        try {
            Assert.notNull(id, ResponseMessages.ID_HOUSING_BLANK);

            return housingRepository.findById(id)
                    .orElseThrow(() -> new AppException(ResponseMessages.HOUSING_NOT_FOUND, HttpStatus.NOT_FOUND));

        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.HOUSING_FETCH_FAILED, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}