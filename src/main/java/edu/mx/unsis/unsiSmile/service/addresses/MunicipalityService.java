package edu.mx.unsis.unsiSmile.service.addresses;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.addresses.MunicipalityRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.MunicipalityResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.addresses.MunicipalityMapper;
import edu.mx.unsis.unsiSmile.model.addresses.MunicipalityModel;
import edu.mx.unsis.unsiSmile.model.addresses.StateModel;
import edu.mx.unsis.unsiSmile.repository.addresses.IMunicipalityRepository;
import edu.mx.unsis.unsiSmile.repository.addresses.IStateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MunicipalityService {

    private final IMunicipalityRepository municipalityRepository;
    private final MunicipalityMapper municipalityMapper;
    private final StateService stateService;
    private final IStateRepository stateRepository;

    @Transactional
    public MunicipalityResponse createMunicipality(@NonNull MunicipalityRequest municipalityRequest) {
        try {
            Assert.notNull(municipalityRequest, "MunicipalityRequest cannot be null");

            // Map the DTO request to the entity
            MunicipalityModel municipalityModel = municipalityMapper.toEntity(municipalityRequest);

            // Save the entity to the database
            MunicipalityModel savedMunicipality = municipalityRepository.save(municipalityModel);

            // Map the saved entity back to a response DTO
            return municipalityMapper.toDto(savedMunicipality);
        } catch (Exception ex) {
            throw new AppException("Failed to create municipality", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public MunicipalityResponse getMunicipalityById(@NonNull String idMunicipality) {
        try {
            MunicipalityModel municipalityModel = municipalityRepository.findByIdMunicipality(idMunicipality)
                    .orElseThrow(() -> new AppException("Municipality not found with ID: " + idMunicipality, HttpStatus.NOT_FOUND));

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
    public Page<MunicipalityResponse> getMunicipalitiesByStateId(@NonNull String stateId, @NonNull Pageable pageable) {
        try {
            StateModel state = stateRepository.findById(stateId)
                    .orElseThrow(() -> new AppException("State not found with ID: " + stateId, HttpStatus.NOT_FOUND));

            Page<MunicipalityModel> municipalityModels = municipalityRepository.findByState(state, pageable);

            return municipalityModels.map(municipalityMapper::toDto);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException("Failed to fetch municipalities by state ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }


    @Transactional(readOnly = true)
    public Page<MunicipalityResponse> getAllMunicipalities(Pageable pageable, String keyword) {
        try {
            Page<MunicipalityModel> municipalities;

            if (keyword != null && !keyword.isEmpty()) {
                municipalities = municipalityRepository.findByKeyword(keyword, pageable);
            } else {
                municipalities = municipalityRepository.findAll(pageable);
            }

            return municipalities.map(municipalityMapper::toDto);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all municipalities", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public MunicipalityResponse updateMunicipality(@NonNull String idMunicipality, @NonNull MunicipalityRequest updatedMunicipalityRequest) {
        try {
            Assert.notNull(updatedMunicipalityRequest, "Updated MunicipalityRequest cannot be null");

            // Find the municipality in the database
            MunicipalityModel municipalityModel = municipalityRepository.findByIdMunicipality(idMunicipality)
                    .orElseThrow(() -> new AppException("Municipality not found with ID: " + idMunicipality, HttpStatus.NOT_FOUND));

            // Update the municipality entity with the new data
            municipalityMapper.updateEntity(updatedMunicipalityRequest, municipalityModel);

            // Save the updated entity
            MunicipalityModel updatedMunicipality = municipalityRepository.save(municipalityModel);

            // Map the updated entity back to a response DTO
            return municipalityMapper.toDto(updatedMunicipality);
        } catch (Exception ex) {
            throw new AppException("Failed to update municipality", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteMunicipalityById(@NonNull String idMunicipality) {
        try {
            // Check if the municipality exists
            if (!municipalityRepository.existsById(idMunicipality)) {
                throw new AppException("Municipality not found with ID: " + idMunicipality, HttpStatus.NOT_FOUND);
            }
            municipalityRepository.deleteById(idMunicipality);
        } catch (Exception ex) {
            throw new AppException("Failed to delete municipality", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public MunicipalityModel findOrCreateMunicipality(@NonNull MunicipalityRequest municipalityRequest) {
        try {
            Assert.notNull(municipalityRequest, ResponseMessages.MUNICIPALITY_NULL);

            String stateId = municipalityRequest.getState().getIdState();
            String municipalityName = municipalityRequest.getName();

            if (stateId != null) {
                MunicipalityModel existingMunicipality = municipalityRepository
                        .findByStateIdAndName(stateId, municipalityName)
                        .orElse(null);

                if (existingMunicipality != null) {
                    return existingMunicipality;
                }
            }

            StateModel state = stateService.findOrCreateState(municipalityRequest.getState());

            MunicipalityModel municipalityModel = municipalityMapper.toModel(municipalityRequest);

            municipalityModel.setState(state);

            return municipalityRepository.save(municipalityModel);

        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.MUNICIPALITY_CREATE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

}
