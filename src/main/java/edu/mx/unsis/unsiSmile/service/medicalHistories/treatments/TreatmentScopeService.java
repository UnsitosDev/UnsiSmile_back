package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;


import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentScopeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentScopeResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments.TreatmentScopeMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentScopeModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentScopeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentScopeService {
    private final ITreatmentScopeRepository treatmentScopeRepository;
    private final ScopeTypeService scopeTypeService;
    public final TreatmentScopeMapper treatmentScopeMapper;

    @Transactional
    public TreatmentScopeResponse createTreatmentScope(TreatmentScopeRequest request) {
        try {
            if (treatmentScopeRepository.existsByScopeName(request.getScopeName())) {
                throw new AppException(ResponseMessages.TREATMENT_SCOPE_NAME_EXISTS, HttpStatus.BAD_REQUEST);
            }

            scopeTypeService.getScopeTypeById(request.getScopeTypeId());

            TreatmentScopeModel model = treatmentScopeMapper.toEntity(request);
            TreatmentScopeModel savedModel = treatmentScopeRepository.save(model);
            return treatmentScopeMapper.toDto(savedModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_TREATMENT_SCOPE, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public TreatmentScopeResponse getTreatmentScopeById(Long id) {
        try {
            TreatmentScopeModel model = treatmentScopeRepository.findById(id)
                    .orElseThrow(() -> new AppException(ResponseMessages.TREATMENT_SCOPE_NOT_FOUND + id, HttpStatus.NOT_FOUND));
            return treatmentScopeMapper.toDto(model);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_SCOPE, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<TreatmentScopeResponse> getAllTreatmentScopes() {
        try {
            List<TreatmentScopeModel> models = treatmentScopeRepository.findAll();
            return treatmentScopeMapper.toDtos(models);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_SCOPES, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<TreatmentScopeResponse> getTreatmentScopesByType(Long scopeTypeId) {
        try {
            List<TreatmentScopeModel> models = treatmentScopeRepository.findByScopeType_IdScopeType(scopeTypeId);
            return treatmentScopeMapper.toDtos(models);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_SCOPES_BY_TYPE, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public TreatmentScopeResponse updateTreatmentScope(Long id, TreatmentScopeRequest request) {
        try {
            TreatmentScopeModel model = treatmentScopeRepository.findById(id)
                    .orElseThrow(() -> new AppException(ResponseMessages.TREATMENT_SCOPE_NOT_FOUND + id, HttpStatus.NOT_FOUND));

            scopeTypeService.getScopeTypeById(request.getScopeTypeId());

            if (!model.getScopeName().equals(request.getScopeName()) &&
                    treatmentScopeRepository.existsByScopeName(request.getScopeName())) {
                throw new AppException(ResponseMessages.TREATMENT_SCOPE_NAME_EXISTS, HttpStatus.BAD_REQUEST);
            }

            treatmentScopeMapper.updateEntity(request, model);
            TreatmentScopeModel updatedModel = treatmentScopeRepository.save(model);
            return treatmentScopeMapper.toDto(updatedModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_TREATMENT_SCOPE, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteTreatmentScopeById(Long id) {
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
