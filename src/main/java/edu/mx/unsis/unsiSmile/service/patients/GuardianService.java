package edu.mx.unsis.unsiSmile.service.patients;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.patients.GuardianRequest;
import edu.mx.unsis.unsiSmile.dtos.response.patients.GuardianResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.patients.GuardianMapper;
import edu.mx.unsis.unsiSmile.model.PersonModel;
import edu.mx.unsis.unsiSmile.model.patients.GuardianModel;
import edu.mx.unsis.unsiSmile.repository.IPersonRepository;
import edu.mx.unsis.unsiSmile.repository.patients.IGuardianRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuardianService {

    private final IGuardianRepository guardianRepository;
    private final GuardianMapper guardianMapper;
    private final IPersonRepository personRepository;

    @Transactional
    public GuardianResponse createGuardian(@NonNull GuardianRequest guardianRequest) {
        try {
            validateGuardianRequest(guardianRequest);

            // Map the DTO request to the entity
            GuardianModel guardianModel = guardianMapper.toEntity(guardianRequest);

            // check if the guardian already exists
            guardianRepository
                    .findByPerson_CurpAndStatusKey(guardianModel.getPerson().getCurp(), "A")
                    .orElseThrow(
                            () -> new AppException(ResponseMessages.GUARDIAN_ALREADY_EXISTS, HttpStatus.BAD_REQUEST));

            PersonModel person = personRepository.findById(guardianModel.getPerson().getCurp()).orElse(null);

            if (person != null) {
                // If the person already exists, set the existing person to the guardian
                guardianModel.setPerson(person);
            }

            // Save the entity to the database
            GuardianModel savedGuardian = guardianRepository.save(guardianModel);

            // Map the saved entity back to a response DTO
            return guardianMapper.toDto(savedGuardian);
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ResponseMessages.GUARDIAN_ALREADY_EXISTS, HttpStatus.CONFLICT, e);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.GUARDIAN_ERROR_CREATING, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    public GuardianModel createGuardianEntity(@NonNull GuardianRequest guardianRequest) {
        try {

            // Map the DTO request to the entity
            GuardianModel guardianModel = guardianMapper.toEntity(guardianRequest);

            PersonModel person = personRepository.findById(guardianModel.getPerson().getCurp()).orElse(null);

            if (person != null) {
                // If the person already exists, set the existing person to the guardian
                guardianModel.setPerson(person);
            }

            // Save the entity to the database
            return guardianRepository.save(guardianModel);

        } catch (Exception ex) {
            throw new AppException(ResponseMessages.GUARDIAN_ERROR_CREATING, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public GuardianResponse getGuardianById(@NonNull Long idGuardian) {
        try {
            GuardianModel guardianModel = guardianRepository.findByIdGuardian(idGuardian)
                    .orElseThrow(
                            () -> new AppException(ResponseMessages.GUARDIAN_NOT_FOUND_ID + idGuardian,
                                    HttpStatus.NOT_FOUND));

            return guardianMapper.toDto(guardianModel);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.GUARDIAN_NOT_FOUND_ID, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<GuardianResponse> getAllGuardians() {
        try {
            List<GuardianModel> allGuardians = guardianRepository.findAll();
            return allGuardians.stream()
                    .map(guardianMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.GUARDIAN_FETCH_FAILED, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public GuardianResponse updateGuardian(@NonNull Long idGuardian, @NonNull GuardianRequest updatedGuardianRequest) {
        try {
            guardianRepository.findById(idGuardian)
                    .orElseThrow(() -> new AppException(ResponseMessages.GUARDIAN_NOT_FOUND, HttpStatus.NOT_FOUND));
            GuardianModel updatedGuardian = updateGuardianModel(idGuardian, updatedGuardianRequest);

            return guardianMapper.toDto(updatedGuardian);
        } catch (Exception e) {
            throw new AppException(ResponseMessages.GUARDIAN_UPDATE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public void deleteGuardianById(@NonNull Long idGuardian) {
        try {
            guardianRepository.findById(idGuardian)
                    .orElseThrow(() -> new AppException(ResponseMessages.GUARDIAN_NOT_FOUND, HttpStatus.NOT_FOUND));
            // Check if the guardian exists
            if (!guardianRepository.existsById(idGuardian)) {
                throw new AppException("Guardian not found with ID: " + idGuardian, HttpStatus.NOT_FOUND);
            }
            guardianRepository.deleteById(idGuardian);
        } catch (Exception e) {
            throw new AppException(ResponseMessages.GUARDIAN_DELETE_ERROR, HttpStatus.INTERNAL_SERVER_ERROR, e);
        }
    }

    @Transactional
    public GuardianModel updateGuardianModel(@NonNull Long idGuardian,
            @NonNull GuardianRequest updatedGuardianRequest) {
        try {
            Assert.notNull(updatedGuardianRequest, ResponseMessages.GUARDIAN_REQUEST_CANNOT_BE_NULL);

            GuardianModel guardianModel = guardianRepository.findByIdGuardian(idGuardian)
                    .orElseThrow(() -> new AppException(ResponseMessages.GUARDIAN_NOT_FOUND, HttpStatus.NOT_FOUND));

            guardianMapper.updateEntity(updatedGuardianRequest, guardianModel);

            return guardianRepository.save(guardianModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.GUARDIAN_UPDATE_FAILED, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public GuardianResponse getGuardianByCurp(String curp) {
        try {
            GuardianModel guardianModel = guardianRepository.findByPerson_CurpAndStatusKey(curp, "A")
                    .orElseThrow(() -> new AppException(ResponseMessages.GUARDIAN_NOT_FOUND, HttpStatus.NOT_FOUND));

            return guardianMapper.toDto(guardianModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.GUARDIAN_FETCH_FAILED, HttpStatus.NOT_FOUND, ex);
        }
    }

    private void validateGuardianRequest(GuardianRequest request) {
        if (request == null || request.getPerson() == null) {
            throw new AppException(ResponseMessages.GUARDIAN_VALIDATION_ERROR, HttpStatus.BAD_REQUEST);
        }
    }
}
