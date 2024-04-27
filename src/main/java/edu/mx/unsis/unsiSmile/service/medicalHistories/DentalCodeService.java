package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.DentalCodeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.DentalCodeResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.DentalCodeMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.DentalCodeModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IDentalCodeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DentalCodeService {

    private final IDentalCodeRepository dentalCodeRepository;
    private final DentalCodeMapper dentalCodeMapper;

    @Transactional
    public DentalCodeResponse createDentalCode(@NonNull DentalCodeRequest dentalCodeRequest) {
        try {
            Assert.notNull(dentalCodeRequest, "DentalCodeRequest cannot be null");

            DentalCodeModel dentalCodeModel = dentalCodeMapper.toEntity(dentalCodeRequest);
            DentalCodeModel savedDentalCode = dentalCodeRepository.save(dentalCodeModel);

            return dentalCodeMapper.toDto(savedDentalCode);
        } catch (Exception ex) {
            throw new AppException("Failed to create dental code", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public DentalCodeResponse getDentalCodeById(@NonNull Long id) {
        try {
            DentalCodeModel dentalCodeModel = dentalCodeRepository.findById(id)
                    .orElseThrow(() -> new AppException("Dental code not found with ID: " + id, HttpStatus.NOT_FOUND));

            return dentalCodeMapper.toDto(dentalCodeModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch dental code", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<DentalCodeResponse> getAllDentalCodes() {
        try {
            List<DentalCodeModel> allDentalCodes = dentalCodeRepository.findAll();
            return allDentalCodes.stream()
                    .map(dentalCodeMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch dental codes", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public DentalCodeResponse updateDentalCode(@NonNull Long id, @NonNull DentalCodeRequest updatedDentalCodeRequest) {
        try {
            Assert.notNull(updatedDentalCodeRequest, "Updated DentalCodeRequest cannot be null");

            DentalCodeModel existingDentalCode = dentalCodeRepository.findById(id)
                    .orElseThrow(() -> new AppException("Dental code not found with ID: " + id, HttpStatus.NOT_FOUND));

            DentalCodeModel updatedDentalCode = dentalCodeMapper.toEntity(updatedDentalCodeRequest);
            updatedDentalCode.setIdDentalCode(existingDentalCode.getIdDentalCode()); // Ensure ID consistency

            DentalCodeModel savedDentalCode = dentalCodeRepository.save(updatedDentalCode);

            return dentalCodeMapper.toDto(savedDentalCode);
        } catch (Exception ex) {
            throw new AppException("Failed to update dental code", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteDentalCode(@NonNull Long id) {
        try {
            if (!dentalCodeRepository.existsById(id)) {
                throw new AppException("Dental code not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            dentalCodeRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete dental code", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public Optional<DentalCodeModel> findByCode(@NonNull String code) {
        try {
            return dentalCodeRepository.findByCode(code);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch dental code by code", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
