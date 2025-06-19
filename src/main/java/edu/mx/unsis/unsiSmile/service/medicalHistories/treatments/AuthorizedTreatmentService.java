package edu.mx.unsis.unsiSmile.service.medicalHistories.treatments;

import edu.mx.unsis.unsiSmile.common.ResponseMessages;
import edu.mx.unsis.unsiSmile.dtos.request.medicalHistories.treatments.TreatmentStatusRequest;
import edu.mx.unsis.unsiSmile.exceptions.AppException;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.AuthorizedTreatmentModel;
import edu.mx.unsis.unsiSmile.model.medicalHistories.treatments.TreatmentDetailModel;
import edu.mx.unsis.unsiSmile.model.professors.ProfessorClinicalAreaModel;
import edu.mx.unsis.unsiSmile.repository.medicalHistories.treatments.IAuthorizedTreatmentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@AllArgsConstructor
@Slf4j
public class AuthorizedTreatmentService {

    private final IAuthorizedTreatmentRepository authorizedTreatmentRepository;

    @Transactional
    public void createAuthorizedTreatment(TreatmentStatusRequest request) {
        try {
            AuthorizedTreatmentModel model = AuthorizedTreatmentModel.builder()
                    .treatmentDetail(TreatmentDetailModel.builder()
                            .idTreatmentDetail(request.getTreatmentDetailId())
                            .build())
                    .status(request.getStatus())
                    .professorClinicalArea(ProfessorClinicalAreaModel.builder()
                            .idProfessorClinicalArea(request.getProfessorClinicalAreaId())
                            .build())
                    .build();

            authorizedTreatmentRepository.save(model);
        } catch (Exception ex) {
            log.error(ResponseMessages.ERROR_CREATING_AUTHORIZED_TREATMENT, ex);
            throw new AppException(ResponseMessages.ERROR_CREATING_AUTHORIZED_TREATMENT, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void updateAuthorizedTreatmentByTreatmentId(TreatmentStatusRequest request) {
        try {
            AuthorizedTreatmentModel authorizedTreatment = authorizedTreatmentRepository
                    .findTopByTreatmentDetail_IdTreatmentDetailOrderByIdAuthorizedTreatmentDesc(request.getTreatmentDetailId())
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.AUTHORIZATION_REQUEST_NOT_FOUND, request.getTreatmentDetailId()),
                            HttpStatus.NOT_FOUND));

            authorizedTreatment.setProfessorClinicalArea(ProfessorClinicalAreaModel.builder()
                    .idProfessorClinicalArea(request.getProfessorClinicalAreaId())
                    .build());

            authorizedTreatmentRepository.save(authorizedTreatment);
        } catch (AppException e) {
            log.warn(String.format(ResponseMessages.AUTHORIZED_TREATMENT_NOT_FOUND, id));
            throw e;
        } catch (Exception ex) {
            log.warn(String.format(ResponseMessages.ERROR_UPDATING_AUTHORIZED_TREATMENT, id));
            throw new AppException(String.format(ResponseMessages.ERROR_UPDATING_AUTHORIZED_TREATMENT, id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public AuthorizedTreatmentModel getAuthorizedTreatmentById(Long id) {
        try {
            return authorizedTreatmentRepository.findById(id)
                    .orElseThrow(() -> new AppException(
                            String.format(ResponseMessages.AUTHORIZED_TREATMENT_NOT_FOUND, id),
                            HttpStatus.NOT_FOUND));
        } catch (AppException e) {
            log.warn(String.format(ResponseMessages.AUTHORIZED_TREATMENT_NOT_FOUND, id));
            throw e;
        } catch (Exception ex) {
            log.error(String.format(ResponseMessages.FAILED_FETCH_AUTHORIZED_TREATMENT, id),
                    ex);
            throw new AppException(String.format(ResponseMessages.FAILED_FETCH_AUTHORIZED_TREATMENT, id),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}