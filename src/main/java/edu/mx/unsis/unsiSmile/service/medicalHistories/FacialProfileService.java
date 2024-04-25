package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.FacialProfileRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.FacialProfileResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.FacialProfileMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.FacialProfileModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IFacialProfileRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FacialProfileService {

    private final IFacialProfileRepository facialProfileRepository;
    private final FacialProfileMapper facialProfileMapper;

    public FacialProfileResponse createFacialProfile(@Valid FacialProfileRequest facialProfileRequest) {
        try {
            FacialProfileModel facialProfileModel = facialProfileMapper.toEntity(facialProfileRequest);
            FacialProfileModel savedFacialProfile = facialProfileRepository.save(facialProfileModel);
            return facialProfileMapper.toDto(savedFacialProfile);
        } catch (DataAccessException ex) {
            throw new AppException("Failed to create facial profile", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    public FacialProfileResponse getFacialProfileById(Long facialProfileId) {
        try {
            FacialProfileModel facialProfileModel = facialProfileRepository.findById(facialProfileId)
                    .orElseThrow(() -> new AppException("Facial Profile not found with ID: " + facialProfileId,
                            HttpStatus.NOT_FOUND));
            return facialProfileMapper.toDto(facialProfileModel);
        } catch (EmptyResultDataAccessException ex) {
            throw new AppException("Facial Profile not found with ID: " + facialProfileId, HttpStatus.NOT_FOUND, ex);
        } catch (DataAccessException ex) {
            throw new AppException("Failed to fetch facial profile", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    public List<FacialProfileResponse> getAllFacialProfiles() {
        try {
            List<FacialProfileModel> allFacialProfiles = facialProfileRepository.findAll();
            return allFacialProfiles.stream()
                    .map(facialProfileMapper::toDto)
                    .collect(Collectors.toList());
        } catch (DataAccessException ex) {
            throw new AppException("Failed to fetch facial profiles", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    public FacialProfileResponse updateFacialProfile(Long facialProfileId, @Valid FacialProfileRequest updatedFacialProfileRequest) {
        try {
            FacialProfileModel existingFacialProfile = facialProfileRepository.findById(facialProfileId)
                    .orElseThrow(() -> new AppException("Facial Profile not found with ID: " + facialProfileId,
                            HttpStatus.NOT_FOUND));

            facialProfileMapper.updateEntity(updatedFacialProfileRequest, existingFacialProfile);
            FacialProfileModel savedFacialProfile = facialProfileRepository.save(existingFacialProfile);
            return facialProfileMapper.toDto(savedFacialProfile);
        } catch (DataAccessException ex) {
            throw new AppException("Failed to update facial profile", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    public void deleteFacialProfile(Long facialProfileId) {
        try {
            facialProfileRepository.deleteById(facialProfileId);
        } catch (EmptyResultDataAccessException ex) {
            throw new AppException("Facial Profile not found with ID: " + facialProfileId, HttpStatus.NOT_FOUND, ex);
        } catch (DataAccessException ex) {
            throw new AppException("Failed to delete facial profile", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
