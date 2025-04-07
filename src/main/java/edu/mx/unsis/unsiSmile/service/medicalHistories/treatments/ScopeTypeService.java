package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.ScopeTypeRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.ScopeTypeResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments.ScopeTypeMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.ScopeTypeModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.IScopeTypeRepository;
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
public class ScopeTypeService {

    private final IScopeTypeRepository scopeTypeRepository;
    private final ScopeTypeMapper scopeTypeMapper;

    @Transactional
    public ScopeTypeResponse createScopeType(@NonNull ScopeTypeRequest scopeTypeRequest) {
        try {
            Assert.notNull(scopeTypeRequest, "ScopeTypeRequest cannot be null");

            if (scopeTypeRepository.existsByName(scopeTypeRequest.getName())) {
                throw new AppException(ResponseMessages.SCOPE_TYPE_NAME_EXISTS, HttpStatus.BAD_REQUEST);
            }

            ScopeTypeModel scopeTypeModel = scopeTypeMapper.toEntity(scopeTypeRequest);
            ScopeTypeModel savedScopeType = scopeTypeRepository.save(scopeTypeModel);

            return scopeTypeMapper.toDto(savedScopeType);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_SCOPE_TYPE, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public ScopeTypeResponse getScopeTypeById(@NonNull Long id) {
        try {
            ScopeTypeModel scopeTypeModel = scopeTypeRepository.findById(id)
                    .orElseThrow(() -> new AppException(ResponseMessages.SCOPE_TYPE_NOT_FOUND + id, HttpStatus.NOT_FOUND));

            return scopeTypeMapper.toDto(scopeTypeModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_SCOPE_TYPE, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<ScopeTypeResponse> getAllScopeTypes() {
        try {
            List<ScopeTypeModel> allScopeTypes = scopeTypeRepository.findAll();
            return allScopeTypes.stream()
                    .map(scopeTypeMapper::toDto)
                    .collect(Collectors.toList());
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_SCOPE_TYPES, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public ScopeTypeResponse updateScopeType(@NonNull Long id, @NonNull ScopeTypeRequest updateScopeTypeRequest) {
        try {
            Assert.notNull(updateScopeTypeRequest, "Update ScopeTypeRequest cannot be null");

            ScopeTypeModel scopeTypeModel = scopeTypeRepository.findById(id)
                    .orElseThrow(() -> new AppException(ResponseMessages.SCOPE_TYPE_NOT_FOUND + id, HttpStatus.NOT_FOUND));

            if (!scopeTypeModel.getName().equals(updateScopeTypeRequest.getName()) &&
                    scopeTypeRepository.existsByName(updateScopeTypeRequest.getName())) {
                throw new AppException(ResponseMessages.SCOPE_TYPE_NAME_DUPLICATED, HttpStatus.BAD_REQUEST);
            }

            scopeTypeMapper.updateEntity(updateScopeTypeRequest, scopeTypeModel);
            ScopeTypeModel updatedScopeType = scopeTypeRepository.save(scopeTypeModel);

            return scopeTypeMapper.toDto(updatedScopeType);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_SCOPE_TYPE, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteScopeTypeById(@NonNull Long id) {
        try {
            if (!scopeTypeRepository.existsById(id)) {
                throw new AppException(ResponseMessages.SCOPE_TYPE_NOT_FOUND + id, HttpStatus.NOT_FOUND);
            }

            scopeTypeRepository.deleteById(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_SCOPE_TYPE, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

}
