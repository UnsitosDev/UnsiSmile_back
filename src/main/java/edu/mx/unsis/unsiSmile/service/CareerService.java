package edu.mx.unsis.unsiSmile.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.CareerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.CareerResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.CareerMapper;
import edu.mx.unsis.unsiSmile.model.CareerModel;
import edu.mx.unsis.unsiSmile.repository.CareerRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CareerService {

    private final CareerRepository careerRepository;
    private final CareerMapper careerMapper;

    @Transactional
    public CareerResponse createCareer(@NonNull CareerRequest careerRequest) {
        try {
            Assert.notNull(careerRequest, "CareerRequest cannot be null");

            // Map the DTO request to the entity
            CareerModel careerModel = careerMapper.toEntity(careerRequest);

            // Save the entity to the database
            CareerModel savedCareer = careerRepository.save(careerModel);

            // Map the saved entity back to a response DTO
            return careerMapper.toDto(savedCareer);
        } catch (Exception ex) {
            throw new AppException("Failed to create career", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public CareerResponse getCareerById(@NonNull Long id) {
        try {

            // Find the career in the database
            CareerModel careerModel = careerRepository.findById(id)
                    .orElseThrow(() -> new AppException("Career not found with ID: " + id, HttpStatus.NOT_FOUND));

            // Map the entity to a response DTO
            return careerMapper.toDto(careerModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch career", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public List<CareerResponse> getAllCareers() {
        try {
            List<CareerModel> allCareers = careerRepository.findAll();
            return allCareers.stream()
                    .map(careerMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch careers", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public CareerResponse updateCareer(@NonNull Long id, @NonNull CareerRequest updatedCareerRequest) {
        try {
            //Assert.hasText(id, "Career ID cannot be null or empty");
            Assert.notNull(updatedCareerRequest, "Updated CareerRequest cannot be null");

            // Find the career in the database
            CareerModel careerModel = careerRepository.findById(id)
                    .orElseThrow(() -> new AppException("Career not found with ID: " + id, HttpStatus.NOT_FOUND));

            // Update the career entity with the new data
            careerMapper.updateEntity(updatedCareerRequest, careerModel);

            // Save the updated entity
            CareerModel updatedCareer = careerRepository.save(careerModel);

            // Map the updated entity back to a response DTO
            return careerMapper.toDto(updatedCareer);
        } catch (Exception ex) {
            throw new AppException("Failed to update career", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void deleteCareerById(@NonNull Long id) {
        try {
            //Assert.hasText(id, "Career ID cannot be null or empty");

            // Check if the career exists
            if (!careerRepository.existsById(id)) {
                throw new AppException("Career not found with ID: " + id, HttpStatus.NOT_FOUND);
            }

            // Delete the career
            careerRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException("Failed to delete career", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
