package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentDetailToothRequest;
import edu.mx.unsis.unsiSmile.dtos.response.medicalHistories.treatments.TreatmentDetailToothResponse;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.mappers.medicalHistories.treatments.TreatmentDetailToothMapper;
import edu.mx.unsis.unsiSmile.model.medicalHistories.teeth.ToothModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailToothModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.teeth.IToothRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailRepository;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.ITreatmentDetailToothRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TreatmentDetailToothService {

    private final ITreatmentDetailToothRepository treatmentDetailToothRepository;
    private final TreatmentDetailToothMapper treatmentDetailToothMapper;
    private final ITreatmentDetailRepository treatmentDetailRepository;
    private final IToothRepository toothRepository;

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
            verifyTreatmentDetailExists(treatmentDetailId);

            List<TreatmentDetailToothModel> models = treatmentDetailToothRepository
                    .findByTreatmentDetail_IdTreatmentDetail(treatmentDetailId);

            return treatmentDetailToothMapper.toDtos(models);
        } catch (AppException e) {
            throw e;
        } catch (Exception ex) {
            throw new AppException(ResponseMessages.FAILED_FETCH_TREATMENT_DETAIL_TEETH, HttpStatus.INTERNAL_SERVER_ERROR, ex);
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

    private void verifyTreatmentDetailExists(@NonNull Long id) {
        treatmentDetailRepository.findById(id)
                .orElseThrow(() -> new AppException(String.format(ResponseMessages.TREATMENT_DETAIL_NOT_FOUND, id), HttpStatus.NOT_FOUND));
    }
}