package edu.mx.unsis.unsiSmile.service.addresses;

import java.util.List;
import java.util.stream.Collectors;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.NeighborhoodResponse;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.StreetRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.StreetResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.addresses.StreetMapper;
import edu.mx.unsis.unsiSmile.model.addresses.StreetModel;
import edu.mx.unsis.unsiSmile.repository.addresses.IStreetRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StreetService {

    private final IStreetRepository streetRepository;
    private final StreetMapper streetMapper;
    private final NeighborhoodService neighborhoodService;

    @Transactional
    public StreetResponse createStreet(@NonNull StreetRequest streetRequest) {
        try {
            Assert.notNull(streetRequest, "StreetRequest cannot be null");

            // Map the DTO request to the entity
            StreetModel streetModel = streetMapper.toEntity(streetRequest);

            // Save the entity to the database
            StreetModel savedStreet = streetRepository.save(streetModel);

            // Map the saved entity back to a response DTO
            return streetMapper.toDto(savedStreet);
        } catch (Exception ex) {
            throw new AppException("Failed to create street", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public StreetResponse getStreetById(@NonNull Long idStreet) {
        try {
            StreetModel streetModel = streetRepository.findByIdStreet(idStreet)
                    .orElseThrow(() -> new AppException("Street not found with ID: " + idStreet, HttpStatus.NOT_FOUND));

            return streetMapper.toDto(streetModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch street by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<StreetResponse> getStreetsByName(@NonNull String name) {
        try {
            List<StreetModel> streetModels = streetRepository.findByName(name);
            return streetModels.stream()
                    .map(streetMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch streets by name", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<StreetResponse> getStreetsByNeighborhood(@NonNull Long neighborhoodId) {
        try {
            NeighborhoodResponse neighborhood = neighborhoodService.getNeighborhoodById(neighborhoodId);
            List<StreetModel> streetModels = streetRepository.findByNeighborhoodIdNeighborhood(
                    neighborhood.getIdNeighborhood());

            return streetModels.stream()
                    .map(streetMapper::toDto)
                    .collect(Collectors.toList());
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to fetch streets by neighborhood", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<StreetResponse> getAllStreets() {
        try {
            List<StreetModel> allStreets = streetRepository.findAll();
            return allStreets.stream()
                    .map(streetMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all streets", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public StreetResponse updateStreet(@NonNull Long idStreet, @NonNull StreetRequest updatedStreetRequest) {
        try {
            Assert.notNull(updatedStreetRequest, "Updated StreetRequest cannot be null");

            // Find the street in the database
            StreetModel streetModel = streetRepository.findByIdStreet(idStreet)
                    .orElseThrow(() -> new AppException("Street not found with ID: " + idStreet, HttpStatus.NOT_FOUND));

            // Update the street entity with the new data
            streetMapper.updateEntity(updatedStreetRequest, streetModel);

            // Save the updated entity
            StreetModel updatedStreet = streetRepository.save(streetModel);

            // Map the updated entity back to a response DTO
            return streetMapper.toDto(updatedStreet);
        } catch (Exception ex) {
            throw new AppException("Failed to update street", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteStreetById(@NonNull Long idStreet) {
        try {
            // Check if the street exists
            if (!streetRepository.existsById(idStreet)) {
                throw new AppException("Street not found with ID: " + idStreet, HttpStatus.NOT_FOUND);
            }
            streetRepository.deleteById(idStreet);
        } catch (Exception ex) {
            throw new AppException("Failed to delete street", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
