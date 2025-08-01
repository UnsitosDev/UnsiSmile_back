package edu.mx.unsis.unsiSmile.service.students;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.students.CareerRequest;
import edu.mx.unsis.unsiSmile.dtos.response.students.CareerResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.students.CareerMapper;
import edu.mx.unsis.unsiSmile.model.students.CareerModel;
import edu.mx.unsis.unsiSmile.repository.students.ICareerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CareerService {

    private final ICareerRepository careerRepository;
    private final CareerMapper careerMapper;

    @Transactional
    public CareerResponse createCareer(@NonNull CareerRequest careerRequest) {
        try {
            Assert.notNull(careerRequest, ResponseMessages.CAREER_REQUEST_CANNOT_BE_NULL);

            // Map the DTO request to the entity
            CareerModel careerModel = careerMapper.toEntity(careerRequest);

            // Save the entity to the database
            CareerModel savedCareer = careerRepository.save(careerModel);

            // Map the saved entity back to a response DTO
            return careerMapper.toDto(savedCareer);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_CREATE_CAREER, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public CareerResponse getCareerById(@NonNull String id) {
        try {

            // Find the career in the database
            CareerModel careerModel = careerRepository.findById(id)
            .orElseThrow(() -> new AppException(ResponseMessages.CAREER_NOT_FOUND_ID + id, HttpStatus.NOT_FOUND));

            // Map the entity to a response DTO
            return careerMapper.toDto(careerModel);
        } catch (AppException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_CAREER, HttpStatus.INTERNAL_SERVER_ERROR, ex);
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
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_CAREERS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public CareerResponse updateCareer(@NonNull String id, @NonNull CareerRequest updatedCareerRequest) {
        try {
            // Assert.hasText(id, "Career ID cannot be null or empty");
            Assert.notNull(updatedCareerRequest, ResponseMessages.CAREER_REQUEST_CANNOT_BE_NULL);

            // Find the career in the database
            CareerModel existingCareer = careerRepository.findById(id)
                .orElseThrow(() -> new AppException(ResponseMessages.CAREER_NOT_FOUND_ID + id, HttpStatus.NOT_FOUND));

            // Update the career entity with the new data
            careerMapper.updateEntity(updatedCareerRequest, existingCareer);

            // Save the updated entity
            CareerModel updatedCareer = careerRepository.save(existingCareer);

            // Map the updated entity back to a response DTO
            return careerMapper.toDto(updatedCareer);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_UPDATE_CAREER, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteCareerById(@NonNull String id) {
        try {
            // Assert.hasText(id, "Career ID cannot be null or empty");

            // Check if the career exists
            if (!careerRepository.existsById(id)) {
                throw new AppException(ResponseMessages.CAREER_NOT_FOUND_ID + id, HttpStatus.NOT_FOUND);
            }

            // Delete the career
            careerRepository.deleteById(id);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_DELETE_CAREER, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public CareerModel getCareerByCareer(@NonNull String career) {
        try {
            // Find the career in the database
            CareerModel careerModel = careerRepository.findByCareer(career)
            .orElseThrow(() -> new AppException(ResponseMessages.CAREER_NOT_FOUND_NAME + career, HttpStatus.NOT_FOUND));

            // Map the entity to a response DTO
            return careerModel;
        } catch (EmptyResultDataAccessException ex) {
            throw new AppException(ResponseMessages.CAREER_NOT_FOUND_NAME + career, HttpStatus.NOT_FOUND, ex);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_TO_FETCH_CAREER, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}