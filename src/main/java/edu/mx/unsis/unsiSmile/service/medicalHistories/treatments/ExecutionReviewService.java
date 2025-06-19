package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentStatusRequest;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.ExecutionReviewModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.IExecutionReviewRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class ExecutionReviewService {
    private final IExecutionReviewRepository executionReviewRepository;

    @Transactional
    public void createExecutionReview(TreatmentStatusRequest request) {
        try {
            ExecutionReviewModel model = ExecutionReviewModel.builder()
                    .treatmentDetail(TreatmentDetailModel.builder()
                            .idTreatmentDetail(request.getTreatmentDetailId())
                            .build())
                    .status(request.getStatus())
                    .professorClinicalArea(ProfessorClinicalAreaModel.builder()
                            .idProfessorClinicalArea(request.getProfessorClinicalAreaId())
                            .build())
                    .build();

            executionReviewRepository.save(model);
        } catch (Exception ex) {
            log.error(ResponseMessages.ERROR_CREATING_TREATMENT_EXECUTION_STATUS, ex);
            throw new AppException(ResponseMessages.ERROR_CREATING_TREATMENT_EXECUTION_STATUS, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public ExecutionReviewModel updateAuthorizedTreatment(Long id, TreatmentStatusRequest request) {
        try {
            ExecutionReviewModel existing = executionReviewRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.TREATMENT_EXECUTION_STATUS_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));

            existing.setComment(request.getComment());
            existing.setStatus(request.getStatus());

            return executionReviewRepository.save(existing);

        } catch (AppException e) {
            log.warn(String.format(ResponseMessages.TREATMENT_EXECUTION_STATUS_NOT_FOUND, id));
            throw e;
        } catch (Exception ex) {
            log.warn(String.format(ResponseMessages.ERROR_UPDATING_TREATMENT_EXECUTION_STATUS, id));
            throw new AppException(String.format(ResponseMessages.ERROR_UPDATING_TREATMENT_EXECUTION_STATUS, id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public ExecutionReviewModel getAuthorizedTreatmentById(Long id) {
        try {
            return executionReviewRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.TREATMENT_EXECUTION_STATUS_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));
        } catch (AppException e) {
            log.warn(String.format(ResponseMessages.TREATMENT_EXECUTION_STATUS_NOT_FOUND, id));
            throw e;
        } catch (Exception ex) {
            log.error(String.format(ResponseMessages.FAILED_FETCH_TREATMENT_EXECUTION_STATUS, id),
                    ex);
            throw new AppException(String.format(ResponseMessages.FAILED_FETCH_TREATMENT_EXECUTION_STATUS, id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
