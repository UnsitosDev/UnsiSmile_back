package edu.mx.unsis.unsiSmile.service.addresses;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.MunicipalityResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.addresses.MunicipalityMapper;
import edu.mx.unsis.unsiSmile.model.MunicipalityModel;
import edu.mx.unsis.unsiSmile.model.StateModel;
import edu.mx.unsis.unsiSmile.repository.addresses.IMunicipalityRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MunicipalityService {

    private final IMunicipalityRepository municipalityRepository;
    private final MunicipalityMapper municipalityMapper;

    @Transactional(readOnly = true)
    public MunicipalityResponse getMunicipalityById(@NonNull String idMunicipality) {
        try {
            MunicipalityModel municipalityModel = municipalityRepository.findByIdMunicipality(idMunicipality)
                    .orElseThrow(() -> new AppException("Municipality not found with ID: " + idMunicipality,
                            HttpStatus.NOT_FOUND));

            return municipalityMapper.toDto(municipalityModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch municipality by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<MunicipalityResponse> getMunicipalitiesByName(@NonNull String name) {
        try {
            List<MunicipalityModel> municipalityModels = municipalityRepository.findByName(name);
            return municipalityModels.stream()
                    .map(municipalityMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch municipalities by name", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<MunicipalityResponse> getMunicipalitiesByState(@NonNull StateModel state) {
        try {
            List<MunicipalityModel> municipalityModels = municipalityRepository.findByState(state);
            return municipalityModels.stream()
                    .map(municipalityMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch municipalities by state", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<MunicipalityResponse> getAllMunicipalities() {
        try {
            List<MunicipalityModel> allMunicipalities = municipalityRepository.findAll();
            return allMunicipalities.stream()
                    .map(municipalityMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all municipalities", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
