package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.Constants;
import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailRequest;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailToothRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailToothResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments.TreatmentDetailToothMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.ReviewStatus;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.ExecutionReviewModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailToothModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.teeth.IToothRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailToothRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreatmentDetailToothService {

    private final ITreatmentDetailToothRepository treatmentDetailToothRepository;
    private final TreatmentDetailToothMapper treatmentDetailToothMapper;
    private final ITreatmentDetailRepository treatmentDetailRepository;
    private final IToothRepository toothRepository;
    private final ExecutionReviewService executionReviewService;

    @Transactional
    public void createTreatmentDetailTeeth(TreatmentDetailToothRequest request) {
        try {
            verifyTreatmentDetailExists(request.getIdTreatmentDetail());

            request.getIdTeeth().forEach(toothId -> {
                if (!toothRepository.existsById(toothId)) {
                    throw new AppException(String.format(ResponseMessages.TOOTH_NOT_FOUND, toothId), HttpStatus.NOT_FOUND);
                }
            });

            if (hasDuplicates(request.getIdTeeth())) {
                throw new AppException(ResponseMessages.DUPLICATE_TEETH_IDS, HttpStatus.BAD_REQUEST);
            }

            request.getIdTeeth().forEach(toothId -> {
                TreatmentDetailToothModel model = treatmentDetailToothMapper.toEntity(request);
                model.setTooth(ToothModel.builder().idTooth(toothId).build());
                treatmentDetailToothRepository.save(model);
            });

        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_CREATE_TREATMENT_DETAIL_TEETH, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional(readOnly = true)
    public List<TreatmentDetailToothResponse> getTreatmentDetailTeethByTreatmentDetail(Long treatmentDetailId) {
        try {
            TreatmentDetailModel treatmentDetail = verifyTreatmentDetailExists(treatmentDetailId);
            ReviewStatus status = treatmentDetail.getStatus();

            if (isAuthorizationStatus(status)) {
                List<TreatmentDetailToothModel> models = treatmentDetailToothRepository
                        .findByTreatmentDetail_IdTreatmentDetail(treatmentDetailId);
                return treatmentDetailToothMapper.toDtos(models);
            }

            ExecutionReviewModel executionReviewModel = executionReviewService
                    .getExecutionReviewModelByTreatmentDetailId(treatmentDetailId);
            Long latestReviewId = executionReviewModel != null
                    ? executionReviewModel.getIdExecutionReview()
                    : null;

            List<TreatmentDetailToothModel> models = treatmentDetailToothRepository
                    .findByTreatmentDetail_IdTreatmentDetail(treatmentDetailId);

            return models.stream()
                    .map(model -> {
                        TreatmentDetailToothResponse dto = treatmentDetailToothMapper.toDto(model);
                        boolean isRecent = model.getStatus() != null &&
                                model.getStatus().getIdExecutionReview().equals(latestReviewId);
                        dto.setIsRecent(isRecent);
                        return dto;
                    })
                    .toList();

        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_DETAIL_TEETH,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void updateTreatmentDetailTeeth(Long treatmentDetailId, TreatmentDetailToothRequest request) {
        try {
            if (!treatmentDetailId.equals(request.getIdTreatmentDetail())) {
                throw new AppException(ResponseMessages.TREATMENT_DETAIL_ID_MISMATCH, HttpStatus.BAD_REQUEST);
            }

            verifyTreatmentDetailExists(request.getIdTreatmentDetail());

            request.getIdTeeth().forEach(toothId -> {
                if (!toothRepository.existsById(toothId)) {
                    throw new AppException(String.format(ResponseMessages.TOOTH_NOT_FOUND, toothId), HttpStatus.NOT_FOUND);
                }
            });

            request.getIdTeeth().forEach(toothId -> {
                TreatmentDetailToothModel model = treatmentDetailToothMapper.toEntity(request);
                model.setTooth(ToothModel.builder().idTooth(toothId).build());
                treatmentDetailToothRepository.save(model);
            });

        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_DETAIL_TEETH, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteTreatmentDetailTeeth(Long treatmentDetailId) {
        try {
            verifyTreatmentDetailExists(treatmentDetailId);

            treatmentDetailToothRepository.deleteByTreatmentDetail_IdTreatmentDetail(treatmentDetailId);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_TREATMENT_DETAIL_TEETH, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    private boolean hasDuplicates(List<String> list) {
        return list.size() != list.stream().distinct().count();
    }

    private TreatmentDetailModel verifyTreatmentDetailExists(@NonNull Long id) {
        return treatmentDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(String.format(ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, id), HttpStatus.NOT_FOUND));
    }

    @Transactional(readOnly = true)
    public List<String> getAllTeethByTreatmentDetailId(Long treatmentDetailId) {
        try {
            verifyTreatmentDetailExists(treatmentDetailId);

            List<TreatmentDetailToothModel> models = treatmentDetailToothRepository
                    .findByTreatmentDetail_IdTreatmentDetail(treatmentDetailId);

            return models.stream()
                    .map(model -> model.getTooth().getIdTooth())
                    .collect(Collectors.toList());
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_DETAIL_TEETH, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteTeethByCodes(Long treatmentDetailId, List<String> toothCodes) {
        try {
            verifyTreatmentDetailExists(treatmentDetailId);

            List<TreatmentDetailToothModel> models = treatmentDetailToothRepository
                    .findByTreatmentDetail_IdTreatmentDetailAndTooth_IdToothIn(treatmentDetailId, toothCodes);

            if (models.isEmpty()) {
                return;
            }

            treatmentDetailToothRepository.deleteAll(models);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_TREATMENT_DETAIL_TEETH, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void deleteAllByTreatmentDetailId(Long treatmentDetailId) {
        try {
            verifyTreatmentDetailExists(treatmentDetailId);
            treatmentDetailToothRepository.deleteByTreatmentDetail_IdTreatmentDetail(treatmentDetailId);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_DELETE_TREATMENT_DETAIL_TEETH,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void updateToothReviewStatus(Long treatmentDetailId, TreatmentDetailToothRequest request, ExecutionReviewModel executionReview) {
        try {
            if (!treatmentDetailId.equals(request.getIdTreatmentDetail())) {
                throw new AppException(ResponseMessages.TREATMENT_DETAIL_ID_MISMATCH, HttpStatus.BAD_REQUEST);
            }

            verifyTreatmentDetailExists(treatmentDetailId);

            request.getIdTeeth().forEach(toothId -> {
                if (!toothRepository.existsById(toothId)) {
                    throw new AppException(String.format(ResponseMessages.TOOTH_NOT_FOUND, toothId), HttpStatus.NOT_FOUND);
                }
            });

            for (String toothId : request.getIdTeeth()) {
                TreatmentDetailToothModel model = treatmentDetailToothRepository
                        .findByTreatmentDetail_IdTreatmentDetailAndTooth_IdTooth(treatmentDetailId, toothId)
                        .orElseThrow(() -> new AppException(
                                String.format(ResponseMessages.TREATMENT_DETAIL_TOOTH_NOT_FOUND, toothId),
                                HttpStatus.NOT_FOUND));
                ExecutionReviewModel status = model.getStatus();
                if (status != null){
                    if(status.getStatus().equals(ReviewStatus.FINISHED)) {
                        throw new AppException(
                                String.format(ResponseMessages.TREATMENT_DETAIL_TOOTH_ALREADY_FINISHED, toothId),
                                HttpStatus.BAD_REQUEST);
                    } else if (status.getStatus().equals(ReviewStatus.IN_REVIEW)) {
                        throw new AppException(
                                String.format(ResponseMessages.TREATMENT_DETAIL_TOOTH_ALREADY_IN_REVIEW, toothId),
                                HttpStatus.BAD_REQUEST);
                    }
                }
                model.setStatus(executionReview);
                model.setEndDate(LocalDateTime.now());
                treatmentDetailToothRepository.save(model);
            }

        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_UPDATE_TREATMENT_DETAIL_TEETH,
                    HttpStatus.INTERNAL_SERVER_ERROR, ex);
        }
    }

    @Transactional
    public void handleTeethByScope(String existingScope, String newScope, Long treatmentDetailId,
                                    TreatmentDetailRequest request) {

        boolean wasTooth = Constants.TOOTH.equals(existingScope);
        boolean isTooth = Constants.TOOTH.equals(newScope);

        if (wasTooth && !isTooth) {
            // CASO 1: eliminar todos los dientes
            deleteAllByTreatmentDetailId(treatmentDetailId);
        } else if (!wasTooth && isTooth) {
            // CASO 2: guardar nuevos dientes
            TreatmentDetailToothRequest toothRequest = request.getTreatmentDetailToothRequest();

            if (toothRequest == null || toothRequest.getIdTeeth() == null || toothRequest.getIdTeeth().isEmpty()) {
                throw new AppException(ResponseMessages.TREATMENT_DETAIL_TOOTH_REQUEST_CANNOT_BE_NULL,
                        HttpStatus.BAD_REQUEST);
            }

            toothRequest.setIdTreatmentDetail(treatmentDetailId);
            createTreatmentDetailTeeth(toothRequest);

        } else if (wasTooth) {
            // CASO 3: actualizar dientes
            TreatmentDetailToothRequest toothRequest = request.getTreatmentDetailToothRequest();

            if (toothRequest == null || toothRequest.getIdTeeth() == null || toothRequest.getIdTeeth().isEmpty()) {
                throw new AppException(ResponseMessages.TREATMENT_DETAIL_TOOTH_REQUEST_CANNOT_BE_NULL,
                        HttpStatus.BAD_REQUEST);
            }

            List<String> currentTeeth = getAllTeethByTreatmentDetailId(treatmentDetailId);
            List<String> updatedTeeth = toothRequest.getIdTeeth();

            List<String> toDelete = currentTeeth.stream()
                    .filter(d -> !updatedTeeth.contains(d))
                    .toList();

            List<String> toAdd = updatedTeeth.stream()
                    .filter(d -> !currentTeeth.contains(d))
                    .toList();

            if (!toDelete.isEmpty()) {
                deleteTeethByCodes(treatmentDetailId, toDelete);
            }

            if (!toAdd.isEmpty()) {
                TreatmentDetailToothRequest toAddRequest = new TreatmentDetailToothRequest();
                toAddRequest.setIdTreatmentDetail(treatmentDetailId);
                toAddRequest.setIdTeeth(toAdd);
                createTreatmentDetailTeeth(toAddRequest);
            }
        }
    }

    private boolean isAuthorizationStatus(ReviewStatus status) {
        return status == ReviewStatus.AWAITING_APPROVAL ||
                status == ReviewStatus.NOT_APPROVED ||
                status == ReviewStatus.APPROVED;
    }

    @Transactional(readOnly = true)
    public boolean canSendToReviewBasedOnTeeth(Long treatmentDetailId) {
        List<TreatmentDetailToothResponse> teeth = getTreatmentDetailTeethByTreatmentDetail(treatmentDetailId);

        // Al menos uno está en estado IN_REVIEW, REJECTED o null(aún sin asignar)
        return teeth.stream().anyMatch(tooth -> {
            String status = tooth.getStatus();
            return status == null
                    || ReviewStatus.IN_PROGRESS.toString().equals(status)
                    || ReviewStatus.REJECTED.toString().equals(status);
        });
    }
}