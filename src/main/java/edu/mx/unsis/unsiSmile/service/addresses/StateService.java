package edu.mx.unsis.unsiSmile.service.addresses;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.request.addresses.StateRequest;
import edu.mx.unsis.unsiSmile.dtos.response.addresses.StateResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.addresses.StateMapper;
import edu.mx.unsis.unsiSmile.model.addresses.StateModel;
import edu.mx.unsis.unsiSmile.repository.addresses.IStateRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StateService {

    private final IStateRepository stateRepository;
    private final StateMapper stateMapper;

    @Transactional
    public StateResponse createState(@NonNull StateRequest stateRequest) {
        try {
            Assert.notNull(stateRequest, "StateRequest cannot be null");

            // Map the DTO request to the entity
            StateModel stateModel = stateMapper.toEntity(stateRequest);

            // Save the entity to the database
            StateModel savedState = stateRepository.save(stateModel);

            // Map the saved entity back to a response DTO
            return stateMapper.toDto(savedState);
        } catch (Exception ex) {
            throw new AppException("Failed to create state", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public StateResponse getStateById(@NonNull String idState) {
        try {
            StateModel stateModel = stateRepository.findByIdState(idState)
                    .orElseThrow(() -> new AppException("State not found with ID: " + idState, HttpStatus.NOT_FOUND));

            return stateMapper.toDto(stateModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch state by ID", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public StateResponse getStateByName(@NonNull String name) {
        try {
            StateModel stateModel = stateRepository.findByName(name)
                    .orElseThrow(() -> new AppException("State not found with name: " + name, HttpStatus.NOT_FOUND));

            return stateMapper.toDto(stateModel);
        } catch (Exception ex) {
            throw new AppException("Failed to fetch state by name", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<StateResponse> getAllStates() {
        try {
            List<StateModel> allStates = stateRepository.findAll();
            return allStates.stream()
                    .map(stateMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException("Failed to fetch all states", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public StateResponse updateState(@NonNull String idState, @NonNull StateRequest updatedStateRequest) {
        try {
            Assert.notNull(updatedStateRequest, "Updated StateRequest cannot be null");

            // Find the state in the database
            StateModel stateModel = stateRepository.findByIdState(idState)
                    .orElseThrow(() -> new AppException("State not found with ID: " + idState, HttpStatus.NOT_FOUND));

            // Update the state entity with the new data
            stateMapper.updateEntity(updatedStateRequest, stateModel);

            // Save the updated entity
            StateModel updatedState = stateRepository.save(stateModel);

            // Map the updated entity back to a response DTO
            return stateMapper.toDto(updatedState);
        } catch (Exception ex) {
            throw new AppException("Failed to update state", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteStateById(@NonNull String idState) {
        try {
            // Check if the state exists
            if (!stateRepository.existsById(idState)) {
                throw new AppException("State not found with ID: " + idState, HttpStatus.NOT_FOUND);
            }
            stateRepository.deleteById(idState);
        } catch (Exception ex) {
            throw new AppException("Failed to delete state", HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
