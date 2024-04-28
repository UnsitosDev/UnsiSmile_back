package edu.mx.unsis.unsiSmile.service.addresses;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.NeighborhoodResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.addresses.NeighborhoodMapper;
import edu.mx.unsis.unsiSmile.model.LocalityModel;
import edu.mx.unsis.unsiSmile.model.NeighborhoodModel;
import edu.mx.unsis.unsiSmile.repository.addresses.INeighborhoodRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NeighborhoodService {

    private final INeighborhoodRepository neighborhoodRepository;
    private final NeighborhoodMapper neighborhoodMapper;

    @Transactional(readOnly = true)
    public NeighborhoodResponse getNeighborhoodById(@NonNull Long idNeighborhood) {
        try {
            NeighborhoodModel neighborhoodModel = neighborhoodRepository.findByIdNeighborhood(idNeighborhood)
                    .orElseThrow(() -> new AppException("Neighborhood not found with ID: " + idNeighborhood, HttpStatus.NOT_FOUND));

            return neighborhoodMapper.toDto(neighborhoodModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch neighborhood by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<NeighborhoodResponse> getNeighborhoodsByName(@NonNull String name) {
        try {
            List<NeighborhoodModel> neighborhoodModels = neighborhoodRepository.findByName(name);
            return neighborhoodModels.stream()
                    .map(neighborhoodMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch neighborhoods by name", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<NeighborhoodResponse> getNeighborhoodsByLocality(@NonNull LocalityModel locality) {
        try {
            List<NeighborhoodModel> neighborhoodModels = neighborhoodRepository.findByLocality(locality);
            return neighborhoodModels.stream()
                    .map(neighborhoodMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch neighborhoods by locality", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<NeighborhoodResponse> getAllNeighborhoods() {
        try {
            List<NeighborhoodModel> allNeighborhoods = neighborhoodRepository.findAll();
            return allNeighborhoods.stream()
                    .map(neighborhoodMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all neighborhoods", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
