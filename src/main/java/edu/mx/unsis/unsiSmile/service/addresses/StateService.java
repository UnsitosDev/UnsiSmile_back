package edu.mx.unsis.unsiSmile.service.addresses;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import edu.mx.unsis.unsiSmile.dtos.response.addresses.StateResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.addresses.StateMapper;
import edu.mx.unsis.unsiSmile.model.StateModel;
import edu.mx.unsis.unsiSmile.repository.addresses.IStateRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StateService {

    private final IStateRepository stateRepository;
    private final StateMapper stateMapper;

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
}

