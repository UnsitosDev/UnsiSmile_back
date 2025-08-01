package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentScopeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentScopeResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments.TreatmentScopeMapper;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentScopeModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentScopeRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreatmentScopeService {

    private final ITreatmentScopeRepository treatmentScopeRepository;
    private final TreatmentScopeMapper treatmentScopeMapper;

    @Transactional
    public TreatmentScopeResponse createTreatmentScope(@NonNull TreatmentScopeRequest TreatmentScopeRequest) {
        try {
            Assert.notNull(TreatmentScopeRequest, "TreatmentScopeRequest cannot be null");

            if (treatmentScopeRepository.existsByName(TreatmentScopeRequest.getName())) {
                throw new AppException(ResponseMessages.TREATMENT_SCOPE_NAME_EXISTS, HttpStatus.BAD_REQUEST);
            }

            TreatmentScopeModel TreatmentScopeModel = treatmentScopeMapper.toEntity(TreatmentScopeRequest);
            TreatmentScopeModel savedTreatmentScope = treatmentScopeRepository.save(TreatmentScopeModel);

            return treatmentScopeMapper.toDto(savedTreatmentScope);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_TREATMENT_SCOPE, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public TreatmentScopeResponse getTreatmentScopeById(@NonNull Long id) {
        try {
            TreatmentScopeModel TreatmentScopeModel = treatmentScopeRepository.findById(id)
                    .orElseThrow(() -> new AppException(ResponseMessages.TREATMENT_SCOPE_NOT_FOUND + id, HttpStatus.NOT_FOUND));

            return treatmentScopeMapper.toDto(TreatmentScopeModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_SCOPE, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<TreatmentScopeResponse> getAllTreatmentScopes() {
        try {
            List<TreatmentScopeModel> allTreatmentScopes = treatmentScopeRepository.findAll();
            return allTreatmentScopes.stream()
                    .map(treatmentScopeMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_SCOPES, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public TreatmentScopeResponse updateTreatmentScope(@NonNull Long id, @NonNull TreatmentScopeRequest updateTreatmentScopeRequest) {
        try {
            Assert.notNull(updateTreatmentScopeRequest, "Update TreatmentScopeRequest cannot be null");

            TreatmentScopeModel TreatmentScopeModel = treatmentScopeRepository.findById(id)
                    .orElseThrow(() -> new AppException(ResponseMessages.TREATMENT_SCOPE_NOT_FOUND + id, HttpStatus.NOT_FOUND));

            if (!TreatmentScopeModel.getName().equals(updateTreatmentScopeRequest.getName()) &&
                    treatmentScopeRepository.existsByName(updateTreatmentScopeRequest.getName())) {
                throw new AppException(ResponseMessages.TREATMENT_SCOPE_NAME_DUPLICATED, HttpStatus.BAD_REQUEST);
            }

            treatmentScopeMapper.updateEntity(updateTreatmentScopeRequest, TreatmentScopeModel);
            TreatmentScopeModel updatedTreatmentScope = treatmentScopeRepository.save(TreatmentScopeModel);

            return treatmentScopeMapper.toDto(updatedTreatmentScope);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_TREATMENT_SCOPE, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteTreatmentScopeById(@NonNull Long id) {
        try {
            if (!treatmentScopeRepository.existsById(id)) {
                throw new AppException(ResponseMessages.TREATMENT_SCOPE_NOT_FOUND + id, HttpStatus.NOT_FOUND);
            }

            treatmentScopeRepository.deleteById(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_TREATMENT_SCOPE, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}
