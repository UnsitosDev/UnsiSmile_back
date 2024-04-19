package edu.mx.unsis.unsiSmile.service;

import edu.mx.unsis.unsiSmile.dtos.request.CycleRequest;
import edu.mx.unsis.unsiSmile.dtos.response.CycleResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.CycleMapper;
import edu.mx.unsis.unsiSmile.model.CycleModel;
import edu.mx.unsis.unsiSmile.repository.ICycleRepository;
import io.jsonwebtoken.lang.Assert;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CycleService {
    private final ICycleRepository cycleRepository;
    private final CycleMapper cycleMapper;

    @Transactional
    public CycleResponse createCycle(@NonNull CycleRequest cycleRequest){
        try {
            Assert.notNull(cycleRequest, "CycleRequest cannot be null");

            // Map the DTO request to the entity
            CycleModel cycleModel = cycleMapper.toEntity(cycleRequest);

            // Save the entity to the database
            CycleModel savedCycle = cycleRepository.save(cycleModel);

            // Map the saved entity back to a response DTO
            return cycleMapper.toDto(savedCycle);
        } catch (Exception ex) {
            throw new AppException("Failed to create cycle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public CycleResponse getCycleById(@NonNull Long id){
        try {
            CycleModel cycleModel = cycleRepository.findById(id)
                    .orElseThrow(() -> new AppException("Cycle not found with ID: " + id, HttpStatus.NOT_FOUND));

            return cycleMapper.toDto(cycleModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch career", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<CycleResponse> getAllCycles(){
        try {
            List<CycleModel> allCycles = cycleRepository.findAll();
            return allCycles.stream()
                    .map(cycleMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch cycles", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public CycleResponse updateCycle(@NonNull Long id, @NonNull CycleRequest updateCycleRequest){
        try {
            Assert.notNull(updateCycleRequest, "Update CycleRequest cannot be null");

            CycleModel cycleModel = cycleRepository.findById(id)
                    .orElseThrow(() -> new AppException("Cycle not found with ID: " + id, HttpStatus.NOT_FOUND));

            cycleMapper.updateEntity(updateCycleRequest, cycleModel);

            CycleModel updatedCycle = cycleRepository.save(cycleModel);

            return cycleMapper.toDto(updatedCycle);
        } catch (Exception ex) {
            throw new AppException("Failed to update cycle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteCycleById(@NonNull Long id){
        try {
            if (!cycleRepository.existsById(id)) {
                throw new AppException("Cycle not found with ID: " + id, HttpStatus.NOT_FOUND);
            }

            cycleRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete cycle", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
