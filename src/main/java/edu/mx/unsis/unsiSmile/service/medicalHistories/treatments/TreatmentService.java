package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.treatments.TreatmentRequest;
import edu.mx.unsis.unsiSmile.dtos.response.treatments.TreatmentResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments.TreatmentMapper;
import edu.mx.unsis.unsiSmile.model.treatments.TreatmentModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.IMedicalRecordCatalogRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreatmentService {

    private final ITreatmentRepository treatmentRepository;
    private final TreatmentMapper treatmentMapper;
    private final TreatmentScopeService treatmentScopeService;
    private final IMedicalRecordCatalogRepository medicalRecordCatalogRepository;

    @Transactional
    public TreatmentResponse createTreatment(TreatmentRequest request) {
        try {
            if (treatmentRepository.existsByName(request.getName())) {
                throw new AppException(ResponseMessages.TREATMENT_NAME_EXISTS, HttpStatus.BAD_REQUEST);
            }

            treatmentScopeService.getTreatmentScopeById(request.getTreatmentScopeId());
            medicalRecordCatalogRepository.findById(request.getMedicalRecordCatalogId())
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.CLINICAL_HISTORY_CATALOG_NOT_FOUND,
                                    request.getMedicalRecordCatalogId()), HttpStatus.NOT_FOUND));

            TreatmentModel model = treatmentMapper.toEntity(request);
            TreatmentModel savedModel = treatmentRepository.save(model);
            return treatmentMapper.toDto(savedModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_TREATMENT, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public TreatmentResponse getTreatmentById(Long id) {
        try {
            TreatmentModel model = treatmentRepository.findById(id)
                    .orElseThrow(() -> new AppException(ResponseMessages.TREATMENT_NOT_FOUND + id, HttpStatus.NOT_FOUND));
            return treatmentMapper.toDto(model);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<TreatmentResponse> getAllTreatments() {
        try {
            List<TreatmentModel> models = treatmentRepository.findAll();
            return treatmentMapper.toDtos(models);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENTS, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<TreatmentResponse> getTreatmentsByTreatmentScope(Long treatmentScopeId) {
        try {
            treatmentScopeService.getTreatmentScopeById(treatmentScopeId);
            List<TreatmentModel> models = treatmentRepository.findByTreatmentScope_IdTreatmentScope(treatmentScopeId);
            return treatmentMapper.toDtos(models);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENTS_BY_SCOPE, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<TreatmentResponse> getTreatmentsByMedicalRecord(Long catalogId) {
        try {
            List<TreatmentModel> models = treatmentRepository.findByMedicalRecordCatalog_IdMedicalRecordCatalog(catalogId);
            return treatmentMapper.toDtos(models);
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENTS_BY_HISTORY, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public TreatmentResponse updateTreatment(Long id, TreatmentRequest request) {
        try {
            TreatmentModel model = treatmentRepository.findById(id)
                    .orElseThrow(() -> new AppException(ResponseMessages.TREATMENT_NOT_FOUND + id, HttpStatus.NOT_FOUND));

            if (!model.getName().equals(request.getName()) &&
                    treatmentRepository.existsByName(request.getName())) {
                throw new AppException(ResponseMessages.TREATMENT_NAME_EXISTS, HttpStatus.BAD_REQUEST);
            }

            treatmentScopeService.getTreatmentScopeById(request.getTreatmentScopeId());
            medicalRecordCatalogRepository.findById(request.getMedicalRecordCatalogId())
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.CLINICAL_HISTORY_CATALOG_NOT_FOUND,
                                    request.getMedicalRecordCatalogId()), HttpStatus.NOT_FOUND));

            treatmentMapper.updateEntity(request, model);
            TreatmentModel updatedModel = treatmentRepository.save(model);
            return treatmentMapper.toDto(updatedModel);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_TREATMENT, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteTreatmentById(Long id) {
        try {
            if (!treatmentRepository.existsById(id)) {
                throw new AppException(ResponseMessages.TREATMENT_NOT_FOUND + id, HttpStatus.NOT_FOUND);
            }
            treatmentRepository.deleteById(id);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_TREATMENT, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public TreatmentModel getTreatmentModelById(Long id) {
        try {
            return treatmentRepository.findById(id)
                    .orElseThrow(() -> new AppException(ResponseMessages.TREATMENT_NOT_FOUND + id, HttpStatus.NOT_FOUND));
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }
}