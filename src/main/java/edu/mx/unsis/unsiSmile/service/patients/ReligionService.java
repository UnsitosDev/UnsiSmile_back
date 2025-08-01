package edu.mx.unsis.unsiSmile.service.patients;

import edu.mx.unsis.unsiSmile.dtos.request.patients.demographics.ReligionRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.demographics.ReligionResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.patients.demographics.ReligionMapper;
import edu.mx.unsis.unsiSmile.model.patients.demographics.ReligionModel;
import edu.mx.unsis.unsiSmile.repository.patients.IReligionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class ReligionService {

    private final IReligionRepository religionRepository;
    private final ReligionMapper religionMapper;

    @Transactional
    public ReligionResponse createReligion(@NonNull ReligionRequest religionRequest) {
        try {
            Assert.notNull(religionRequest, "ReligionRequest cannot be null");

            // Map the DTO request to the entity
            ReligionModel religionModel = religionMapper.toEntity(religionRequest);

            // Save the entity to the database
            ReligionModel savedReligion = religionRepository.save(religionModel);

            // Map the saved entity back to a response DTO
            return religionMapper.toDto(savedReligion);
        } catch (Exception ex) {
            throw new AppException("Failed to create religion", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public ReligionResponse getReligionById(@NonNull Long idReligion) {
        try {
            ReligionModel religionModel = religionRepository.findByIdReligion(idReligion)
                    .orElseThrow(() -> new AppException("Religion not found with ID: " + idReligion, HttpStatus.NOT_FOUND));

            return religionMapper.toDto(religionModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch religion by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public ReligionResponse getReligionByName(@NonNull String religion) {
        try {
            ReligionModel religionModel = religionRepository.findByReligion(religion)
                    .orElseThrow(() -> new AppException("Religion not found with name: " + religion, HttpStatus.NOT_FOUND));

            return religionMapper.toDto(religionModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch religion by name", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public Page<ReligionResponse> getAllReligions(Pageable pageable, String keyword) {
        try {
            Page<ReligionModel> religions;
            if(keyword != null && !keyword.isEmpty()){
                religions = religionRepository.findByKeyword(keyword, pageable);
            } else {
                religions = religionRepository.findAll(pageable);
            }
            return religions.map(religionMapper::toDto);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all religions", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public ReligionResponse updateReligion(@NonNull Long idReligion, @NonNull ReligionRequest updatedReligionRequest) {
        try {
            Assert.notNull(updatedReligionRequest, "Updated ReligionRequest cannot be null");

            // Find the religion in the database
            ReligionModel religionModel = religionRepository.findByIdReligion(idReligion)
                    .orElseThrow(() -> new AppException("Religion not found with ID: " + idReligion, HttpStatus.NOT_FOUND));

            // Update the religion entity with the new data
            religionMapper.updateEntity(updatedReligionRequest, religionModel);

            // Save the updated entity
            ReligionModel updatedReligion = religionRepository.save(religionModel);

            // Map the updated entity back to a response DTO
            return religionMapper.toDto(updatedReligion);
        } catch (Exception ex) {
            throw new AppException("Failed to update religion", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteReligionById(@NonNull Long idReligion) {
        try {
            // Check if the religion exists
            if (!religionRepository.existsById(idReligion)) {
                throw new AppException("Religion not found with ID: " + idReligion, HttpStatus.NOT_FOUND);
            }
            religionRepository.deleteById(idReligion);
        } catch (Exception ex) {
            throw new AppException("Failed to delete religion", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
