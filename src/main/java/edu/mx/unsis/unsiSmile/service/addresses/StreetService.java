package edu.mx.unsis.unsiSmile.service.addresses;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.StreetResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.addresses.StreetMapper;
import edu.mx.unsis.unsiSmile.model.NeighborhoodModel;
import edu.mx.unsis.unsiSmile.model.StreetModel;
import edu.mx.unsis.unsiSmile.repository.addresses.IStreetRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StreetService {

    private final IStreetRepository streetRepository;
    private final StreetMapper streetMapper;

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
    public List<StreetResponse> getStreetsByNeighborhood(@NonNull NeighborhoodModel neighborhood) {
        try {
            List<StreetModel> streetModels = streetRepository.findByNeighborhood(neighborhood);
            return streetModels.stream()
                    .map(streetMapper::toDto)
                    .collect(Collectors.toList());
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
}
