package edu.mx.unsis.unsiSmile.service.addresses;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.LocalityResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.addresses.LocalityMapper;
import edu.mx.unsis.unsiSmile.model.LocalityModel;
import edu.mx.unsis.unsiSmile.model.MunicipalityModel;
import edu.mx.unsis.unsiSmile.repository.addresses.ILocalityRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocalityService {

    private final ILocalityRepository localityRepository;
    private final LocalityMapper localityMapper;

    @Transactional(readOnly = true)
    public LocalityResponse getLocalityById(@NonNull String idLocality) {
        try {
            LocalityModel localityModel = localityRepository.findByIdLocality(idLocality)
                    .orElseThrow(
                            () -> new AppException("Locality not found with ID: " + idLocality, HttpStatus.NOT_FOUND));

            return localityMapper.toDto(localityModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch locality by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<LocalityResponse> getLocalitiesByName(@NonNull String name) {
        try {
            List<LocalityModel> localityModels = localityRepository.findByName(name);
            return localityModels.stream()
                    .map(localityMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch localities by name", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<LocalityResponse> getLocalitiesByPostalCode(@NonNull String postalCode) {
        try {
            List<LocalityModel> localityModels = localityRepository.findByPostalCode(postalCode);
            return localityModels.stream()
                    .map(localityMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch localities by postal code", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<LocalityResponse> getLocalitiesByMunicipality(@NonNull MunicipalityModel municipality) {
        try {
            List<LocalityModel> localityModels = localityRepository.findByMunicipality(municipality);
            return localityModels.stream()
                    .map(localityMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch localities by municipality", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<LocalityResponse> getAllLocalities() {
        try {
            List<LocalityModel> allLocalities = localityRepository.findAll();
            return allLocalities.stream()
                    .map(localityMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all localities", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
