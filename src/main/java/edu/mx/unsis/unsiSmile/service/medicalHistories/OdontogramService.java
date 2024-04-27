package edu.mx.unsis.unsiSmile.service.medicalHistories;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.OdontogramRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.OdontogramResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.OdontogramMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.OdontogramModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IOdontogramRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OdontogramService {

    private final IOdontogramRepository odontogramRepository;
    private final OdontogramMapper odontogramMapper;

    @Transactional
    public OdontogramResponse createOdontogram(@NonNull OdontogramRequest odontogramRequest) {
        try {
            Assert.notNull(odontogramRequest, "OdontogramRequest cannot be null");

            OdontogramModel odontogramModel = odontogramMapper.toEntity(odontogramRequest);
            odontogramModel.setDate(LocalDate.now());
            OdontogramModel savedOdontogram = odontogramRepository.save(odontogramModel);

            return odontogramMapper.toDto(savedOdontogram);
        } catch (Exception ex) {
            throw new AppException("Failed to create odontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public OdontogramResponse getOdontogramById(@NonNull Long id) {
        try {
            OdontogramModel odontogramModel = odontogramRepository.findById(id)
                    .orElseThrow(() -> new AppException("Odontogram not found with ID: " + id, HttpStatus.NOT_FOUND));

            return odontogramMapper.toDto(odontogramModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch odontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<OdontogramResponse> getAllOdontograms() {
        try {
            List<OdontogramModel> allOdontograms = odontogramRepository.findAll();
            return allOdontograms.stream()
                    .map(odontogramMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch odontograms", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public OdontogramResponse updateOdontogram(@NonNull Long id, @NonNull OdontogramRequest updatedOdontogramRequest) {
        try {
            Assert.notNull(updatedOdontogramRequest, "Updated OdontogramRequest cannot be null");

            OdontogramModel existingOdontogram = odontogramRepository.findById(id)
                    .orElseThrow(() -> new AppException("Odontogram not found with ID: " + id, HttpStatus.NOT_FOUND));

            OdontogramModel updatedOdontogram = odontogramMapper.toEntity(updatedOdontogramRequest);
            updatedOdontogram.setIdOdontogram(existingOdontogram.getIdOdontogram()); // Ensure ID consistency

            OdontogramModel savedOdontogram = odontogramRepository.save(updatedOdontogram);

            return odontogramMapper.toDto(savedOdontogram);
        } catch (Exception ex) {
            throw new AppException("Failed to update odontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteOdontogram(@NonNull Long id) {
        try {
            if (!odontogramRepository.existsById(id)) {
                throw new AppException("Odontogram not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
            odontogramRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete odontogram", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
